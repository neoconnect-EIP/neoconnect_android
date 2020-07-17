package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.OfferAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ListOffer : Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_offer, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getOffers(token!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_list_offer).visibility =
                                View.VISIBLE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_list_offer).visibility =
                                View.GONE
                            Log.i(
                                "List Offer",
                                "Récupération des offres réussie"
                            )
                            val recyclerListView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListOffer)
                            recyclerListView.layoutManager =
                                GridLayoutManager(context, 2)
                            val adapter = OfferAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_list_offer).visibility =
                            View.VISIBLE
                        inflate.findViewById<RecyclerView>(R.id.recyclerListOffer).visibility =
                            View.GONE
                        Log.e("List Offer", it.message)
                    }
                }
            }
        })
        return inflate
    }
}
