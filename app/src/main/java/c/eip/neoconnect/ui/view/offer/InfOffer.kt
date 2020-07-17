package c.eip.neoconnect.ui.view.offer


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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.OfferAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class InfOffer : Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_inf_offer, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)
        val userId = DataGetter.INSTANCE.getUserId(context!!)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getMyOffersInf(token!!, userId).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_list_my_offers).visibility =
                                View.VISIBLE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_list_my_offers).visibility =
                                View.GONE
                            Log.i(
                                "List Offer",
                                "Récupération des ${it.data.size} offres de $userId"
                            )
                            val recyclerListView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInf)
                            recyclerListView.layoutManager = GridLayoutManager(context, 2)
                            val adapter = OfferAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_list_my_offers).visibility =
                            View.VISIBLE
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