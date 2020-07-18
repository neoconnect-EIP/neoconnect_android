package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.OfferAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ListSortedOffer : Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_sorted_offer, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)

        var sex: String? = null
        var brand: String? = null
        var color: String? = null
        if (!arguments!!.isEmpty) {
            if (arguments?.get("sex") != null) {
                sex = arguments?.get("sex") as String
            } else if (arguments?.get("brand") != null) {
                brand = arguments?.get("brand") as String
            } else if (arguments?.get("color") != null) {
                color = arguments?.get("color") as String
            }
        }
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getOffers(token!!, sex, color, brand).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_list_sorted_offer).visibility =
                                View.VISIBLE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_list_sorted_offer).visibility =
                                View.GONE
                            Log.i(
                                "List Sorted Offer",
                                "Récupération des offres réussie"
                            )
                            val recyclerSortedListView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerSortedListOffer)
                            recyclerSortedListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapter = OfferAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerSortedListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_list_sorted_offer).visibility =
                            View.VISIBLE
                        inflate.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility =
                            View.GONE
                        Log.e("List Sorted Offer", it.message)
                    }
                }
            }
        })
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
    }


}
