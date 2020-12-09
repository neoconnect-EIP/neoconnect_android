package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import c.eip.neoconnect.ui.adapter.ListInfAdapter
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Resource
import c.eip.neoconnect.utils.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class ListInf : Fragment() {
    private lateinit var listViewModel: ListViewModel
    private lateinit var infViewModel: InfViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_inf, container, false)
        inflate.findViewById<TextInputEditText>(R.id.searchKeyword).setHint(R.string.searchInf)
        getListInf(view = inflate)
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.filterInfButton).setOnClickListener {
            filterOptions(view = view)
        }
        val searchKeyword = view.findViewById<TextInputEditText>(R.id.searchKeyword)
        searchKeyword.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val token = DataGetter.INSTANCE.getToken(requireContext())
                    val search = SearchModel()
                    search.pseudo = searchKeyword.text.toString()
                    val viewModel: LiveData<Resource<SearchResponseModel>>
                    infViewModel = ViewModelProvider(this).get(InfViewModel::class.java)
                    viewModel = infViewModel.searchInf(token = token!!, keyword = search)
                    viewModel.observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    Search.searchResponse = it.data
                                    val bundle =
                                        bundleOf("mode" to 1, "id" to Search.searchResponse?.id)
                                    when (it.data?.userType) {
                                        "influencer" -> {
                                            findNavController().navigate(
                                                R.id.navigation_other_profil_inf,
                                                bundle
                                            )
                                        }
                                        else -> {
                                            Toast.makeText(
                                                context,
                                                "Imposible de récupérer cet utilisateur",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Log.e("Search User", it.message!!)
                                        }
                                    }
                                }
                                Status.ERROR -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                                        .show()
                                    Log.e("Search User", it.message!!)
                                }
                            }
                        }
                    })
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Récupération de la liste des Influenceurs
     */
    private fun getListInf(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListInf)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getListInf(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                                View.VISIBLE
                            view.findViewById<LinearLayout>(R.id.searchKeywordLayout).visibility =
                                View.GONE
                            view.findViewById<FloatingActionButton>(R.id.filterInfButton).visibility =
                                View.GONE
                        } else {
                            view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                                View.GONE
                            Log.i(
                                "List Inf",
                                "Récupération des influenceurs réussie"
                            )
                            infs = it.data
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapter = ListInfAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        view.findViewById<FloatingActionButton>(R.id.filterInfButton).visibility =
                            View.GONE
                        view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                            View.VISIBLE
                        view.findViewById<LinearLayout>(R.id.searchKeywordLayout).visibility =
                            View.GONE
                        recyclerListView.visibility = View.GONE
                        view.findViewById<TextInputEditText>(R.id.searchKeyword).visibility =
                            View.GONE
                        Log.e("List Inf", it.message!!)
                    }
                }
            }
        })
    }

    private fun filterInfs(view: View, theme: String) {
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListInf)
        recyclerListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter: ListInfAdapter
        val listInf = ArrayList<InfluenceurResponseModel>()
        adapter = if (theme == "") {
            ListInfAdapter(infs)
        } else {
            infs.forEach { element ->
                if (element.theme == theme) {
                    listInf.add(element)
                }
            }
            ListInfAdapter(listInf)
        }
        adapter.notifyDataSetChanged()
        recyclerListView.adapter = adapter
    }

    private fun filterOptions(view: View) {
        val popupMenu = PopupMenu(context, view.findViewById(R.id.filterInfButton))
        popupMenu.menuInflater.inflate(R.menu.theme_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filterEmpty -> filterInfs(view = view, theme = "")
                R.id.filterMode -> filterInfs(view = view, theme = "Mode")
                R.id.filterCosmétique -> filterInfs(view = view, theme = "Cosmétique")
                R.id.filterHightech -> filterInfs(view = view, theme = "High tech")
                R.id.filterVideoGames -> filterInfs(view = view, theme = "Jeux Vidéo")
                R.id.filterFood -> filterInfs(view = view, theme = "Nourriture")
                R.id.filterHealthy -> filterInfs(view = view, theme = "Sport/Fitness")
            }
            true
        }
        popupMenu.show()
    }

    companion object {
        var infs = ArrayList<InfluenceurResponseModel>()
    }
}
