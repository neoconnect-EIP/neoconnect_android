package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.ui.adapter.OfferAdapter
import c.eip.neoconnect.ui.adapter.OfferSuggestionAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class ListOffer : Fragment() {
    private lateinit var viewModel: ListViewModel
    private var offerName: String? = null

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_offer, container, false)
        inflate.findViewById<TextInputEditText>(R.id.searchKeyword).setHint(R.string.searchOffer)
        getSuggestions(view = inflate)
        getListOffers(view = inflate)
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.filterOfferButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_list_sorted_offer)
        }
        val searchKeyword = view.findViewById<TextInputEditText>(R.id.searchKeyword)
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListOffer)
        recyclerListView.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

        searchKeyword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var offerList: ArrayList<OffreResponseModel> = ArrayList()
                if (!searchKeyword.text.toString().isBlank()) {
                    offers.forEach { element ->
                        if (element.productName != null) {
                            if (element.productName!!.toLowerCase(Locale.getDefault()).contains(
                                    searchKeyword.text.toString().toLowerCase(Locale.getDefault())
                                )
                            ) {
                                offerList.add(element)
                                val adapter = OfferAdapter(offerList, "list", "inf")
                                adapter.notifyDataSetChanged()
                                recyclerListView.adapter = adapter
                            }
                        }
                    }
                } else {
                    offerList = offers
                    val adapter = OfferAdapter(offerList, "list", "inf")
                    adapter.notifyDataSetChanged()
                    recyclerListView.adapter = adapter
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


    }

    /**
     * Récupération suggestions
     */
    private fun getSuggestions(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListOfferSuggestion)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.suggestionOffer(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isNullOrEmpty()) {
                            recyclerListView.visibility = View.GONE
                        } else {
                            recyclerListView.visibility = View.VISIBLE
                            Log.i("Suggestion Offer", it.message.toString())
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val adapter = OfferSuggestionAdapter(it.data, "list")
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        recyclerListView.visibility = View.GONE
                        Log.e("Suggestion Offer", it.message!!)
                    }
                }
            }
        })
    }

    /**
     * Récupération de la liste des Offres
     */
    private fun getListOffers(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListOffer)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getOffers(token = token!!, sex = null, color = null, brand = null, subject = null)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (it.data!!.isEmpty()) {
                                view.findViewById<TextView>(R.id.pb_list_offer).visibility =
                                    View.VISIBLE
                                view.findViewById<FloatingActionButton>(R.id.filterOfferButton).visibility =
                                    View.GONE
                            } else {
                                view.findViewById<TextView>(R.id.pb_list_offer).visibility =
                                    View.GONE
                                Log.i(
                                    "List Offer",
                                    "Récupération des offres réussie"
                                )
                                offers = it.data
                                recyclerListView.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                val adapter = OfferAdapter(offers, "list", "inf")
                                adapter.notifyDataSetChanged()
                                recyclerListView.adapter = adapter
                            }
                        }
                        Status.ERROR -> {
                            view.findViewById<FloatingActionButton>(R.id.filterOfferButton).visibility =
                                View.GONE
                            view.findViewById<TextView>(R.id.pb_list_offer).visibility =
                                View.VISIBLE
                            recyclerListView.visibility = View.GONE
                            Log.e("List Offer", it.message!!)
                        }
                    }
                }
            })
    }

    companion object {
        var offers = ArrayList<OffreResponseModel>()
    }
}

