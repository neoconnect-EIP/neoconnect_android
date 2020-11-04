package c.eip.neoconnect.ui.view.offer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
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

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_inf_offer, container, false)
        getOfferApplied(inflate = inflate)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Liste des offres postulées par un influenceur
     */
    private fun getOfferApplied(inflate: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val userId = DataGetter.INSTANCE.getUserId(requireContext())
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getMyOffersInf(token = token!!, id = userId)
            .observe(viewLifecycleOwner, Observer {
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
                                    ArrayList()
                                val listAccepted: ArrayList<OffreResponseModel> =
                                    ArrayList()
                                val listRefused: ArrayList<OffreResponseModel> =
                                    ArrayList()
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

                                listOffers(
                                    offers = listPending,
                                    recyclerView = inflate.findViewById(R.id.recyclerListMyOfferInfPending),
                                    errorText = inflate.findViewById(R.id.pb_applied_offer_pending)
                                )

                                listOffers(
                                    offers = listAccepted,
                                    recyclerView = inflate.findViewById(R.id.recyclerListMyOfferInfAccepted),
                                    errorText = inflate.findViewById(R.id.pb_applied_offer_accepted)
                                )

                                listOffers(
                                    offers = listRefused,
                                    recyclerView = inflate.findViewById(R.id.recyclerListMyOfferInfRefused),
                                    errorText = inflate.findViewById(R.id.pb_applied_offer_refused)
                                )
                            }
                        }
                        Status.ERROR -> {
                            inflate.findViewById<TextView>(R.id.pb_list_my_offers).visibility =
                                View.VISIBLE
                        }
                    }
                }
            })
    }

    /**
     * Séparation de l'affichage en Offre Accepté, Offre Refusés et Offre en attente
     */
    private fun listOffers(
        offers: ArrayList<OffreResponseModel>,
        recyclerView: RecyclerView,
        errorText: TextView
    ) {
        if (offers.isNullOrEmpty()) {
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            errorText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val adapter = OfferAdapter(offers, "applied")
            adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        }
    }
}
