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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreResponseModel
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
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val userId = DataGetter.INSTANCE.getUserId(requireContext())
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getMyOffersInf(token!!, userId).observe(viewLifecycleOwner, Observer {
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
                            val listPending: ArrayList<OffreResponseModel> =
                                ArrayList<OffreResponseModel>()
                            val listAccepted: ArrayList<OffreResponseModel> =
                                ArrayList<OffreResponseModel>()
                            val listRefused: ArrayList<OffreResponseModel> =
                                ArrayList<OffreResponseModel>()
                            it.data.forEach { offre ->
                                run {
                                    when (offre.status) {
                                        "pending" -> listPending.add(offre)
                                        "accepted" -> listAccepted.add(offre)
                                        "refused" -> listRefused.add(offre)
                                        else -> Log.e("Status", "Status error")
                                    }
                                }
                            }

                            val recyclerListPending =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfPending)
                            recyclerListPending.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapterPending = OfferAdapter(listPending, "applied")
                            adapterPending.notifyDataSetChanged()
                            recyclerListPending.adapter = adapterPending

                            val recyclerListAccepted =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfAccepted)
                            recyclerListAccepted.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapterAccepted = OfferAdapter(listAccepted, "applied")
                            adapterAccepted.notifyDataSetChanged()
                            recyclerListAccepted.adapter = adapterAccepted

                            val recyclerListRefused =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfRefused)
                            recyclerListRefused.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapterRefused = OfferAdapter(listRefused, "applied")
                            adapterRefused.notifyDataSetChanged()
                            recyclerListRefused.adapter = adapterRefused
                            
                            if (listPending.isEmpty()) {
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfPending).visibility = View.GONE
                                inflate.findViewById<TextView>(R.id.pb_applied_offer_pending).visibility = View.VISIBLE
                            }
                            if (listAccepted.isEmpty()) {
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfAccepted).visibility = View.GONE
                                inflate.findViewById<TextView>(R.id.pb_applied_offer_accepted).visibility = View.VISIBLE
                            }
                            if (listRefused.isEmpty()) {
                                inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferInfRefused).visibility = View.GONE
                                inflate.findViewById<TextView>(R.id.pb_applied_offer_refused).visibility = View.VISIBLE
                            }
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
