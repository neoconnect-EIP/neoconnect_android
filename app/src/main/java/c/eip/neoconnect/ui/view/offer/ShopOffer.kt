package c.eip.neoconnect.ui.view.offer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
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

class ShopOffer : Fragment() {
    private lateinit var viewModel: ListViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_shop_offer, container, false)
        val userType = DataGetter.INSTANCE.getUserType(requireContext())
        if (userType == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.layoutShopOffer)
                .setBackgroundResource(R.drawable.background_shop)
            val userId = DataGetter.INSTANCE.getUserId(requireContext())
            getShopOffer(inflate = inflate, userId = userId, userType = "shop")
        } else if (userType == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.layoutShopOffer)
                .setBackgroundResource(R.drawable.background_influencer)
            val id = arguments?.get("id") as Int
            getShopOffer(inflate = inflate, userId = id, userType = "inf")
        }
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
     * Liste des offres postées par une marque
     */
    private fun getShopOffer(inflate: View, userId: Int, userType: String) {
        val token = DataGetter.INSTANCE.getToken(requireContext())

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getMyOffersShop(token = token!!, id = userId)
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
                                val recyclerListView =
                                    inflate.findViewById<RecyclerView>(R.id.recyclerListMyOfferShop)
                                recyclerListView.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                val adapter = OfferAdapter(it.data, "list", userType)
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
    }
}
