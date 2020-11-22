package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
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
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import c.eip.neoconnect.ui.adapter.ListShopAdapter
import c.eip.neoconnect.ui.adapter.ShopSuggestionAdapter
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Resource
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class ListShop : Fragment() {
    private lateinit var listViewModel: ListViewModel
    private lateinit var shopViewModel: ShopViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_shop, container, false)
        inflate.findViewById<TextInputEditText>(R.id.searchKeyword).setHint(R.string.searchShop)
        getSuggestions(view = inflate)
        getListShop(view = inflate)
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchKeyword = view.findViewById<TextInputEditText>(R.id.searchKeyword)
        searchKeyword.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val token = DataGetter.INSTANCE.getToken(requireContext())
                    val search = SearchModel()
                    search.pseudo = searchKeyword.text.toString()
                    val viewModel: LiveData<Resource<SearchResponseModel>>
                    shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
                    viewModel = shopViewModel.searchShop(token = token!!, keyword = search)
                    viewModel.observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    Search.searchResponse = it.data
                                    val bundle =
                                        bundleOf("mode" to 1, "id" to Search.searchResponse?.id)
                                    when (it.data?.userType) {
                                        "shop" -> {
                                            findNavController().navigate(
                                                R.id.navigation_other_profil_shop,
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
     * Récupération suggestions
     */
    private fun getSuggestions(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListShopSuggestion)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.suggestionUser(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isNullOrEmpty()) {
                            recyclerListView.visibility = View.GONE
                        } else {
                            recyclerListView.visibility = View.VISIBLE
                            Log.i("Suggestion Shop", it.message.toString())
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val adapter = ShopSuggestionAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        recyclerListView.visibility = View.GONE
                        Log.e("Suggestion Shop", it.message!!)
                    }
                }
            }
        })
    }

    /**
     * Récupération de la liste des Marques
     */
    private fun getListShop(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListShop)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getListShop(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            view.findViewById<TextView>(R.id.pb_list_shop).visibility =
                                View.VISIBLE
                            view.findViewById<LinearLayout>(R.id.searchKeywordLayout).visibility =
                                View.GONE
                        } else {
                            view.findViewById<TextView>(R.id.pb_list_shop).visibility =
                                View.GONE
                            Log.i(
                                "List Shop",
                                "Récupération des marques réussie"
                            )
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapter = ListShopAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        view.findViewById<TextView>(R.id.pb_list_shop).visibility =
                            View.VISIBLE
                        recyclerListView.visibility = View.GONE
                        view.findViewById<LinearLayout>(R.id.searchKeywordLayout).visibility =
                            View.GONE
                        view.findViewById<TextInputEditText>(R.id.searchKeyword).visibility =
                            View.GONE

                        Log.e("List Shop", it.message!!)
                    }
                }
            }
        })
    }
}
