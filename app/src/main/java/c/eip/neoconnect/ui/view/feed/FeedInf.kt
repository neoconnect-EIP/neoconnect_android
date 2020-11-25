package c.eip.neoconnect.ui.view.feed


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.ui.adapter.FeedOfferAdapter
import c.eip.neoconnect.ui.adapter.FeedShopAdapter
import c.eip.neoconnect.ui.viewModel.FeedViewModel
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class FeedInf : Fragment() {
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var infViewModel: InfViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_feed_inf, container, false)
        val token = DataGetter.INSTANCE.getToken(requireContext())

        infViewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        infViewModel.getProfilInf(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        influenceurData = it.data
                        val title = "Bonjour, ${influenceurData?.pseudo}"
                        inflate.findViewById<TextView>(R.id.titleFeed).text = title
                    }
                    Status.ERROR -> {
                    }
                }

            }
        })

        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        feedViewModel.getFeed(token = token).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        feedOffres(
                            listOffer = it.data?.listOfferTendance,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListOffresTendances),
                            errorText = inflate.findViewById(R.id.pb_feed_offer_tendance)
                        )

                        feedOffres(
                            listOffer = it.data?.listOfferPopulaire,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListOffresPopulaires),
                            errorText = inflate.findViewById(R.id.pb_feed_offer_populaire)
                        )

//                        feedOffres(
//                            listOffer = it.data?.listOfferNotes,
//                            recyclerView = inflate.findViewById(R.id.recyclerFeedListOffresNotes),
//                            errorText = inflate.findViewById(R.id.pb_feed_offer_notes)
//                        )

                        inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresNotes).visibility =
                            View.GONE
                        inflate.findViewById<TextView>(R.id.pb_feed_offer_notes).visibility =
                            View.GONE
                        feedShop(
                            listShop = it.data?.listShopTendance,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListShopTendances),
                            errorText = inflate.findViewById(R.id.pb_feed_shop_tendance)
                        )

                        feedShop(
                            listShop = it.data?.listShopPopulaire,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListShopPopulaires),
                            errorText = inflate.findViewById(R.id.pb_feed_shop_populaire)
                        )

                        feedShop(
                            listShop = it.data?.listShopNotes,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListShopNotes),
                            errorText = inflate.findViewById(R.id.pb_feed_shop_notes)
                        )
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_feed).visibility = View.VISIBLE
                        inflate.findViewById<ScrollView>(R.id.feedLayout).visibility = View.GONE
                        Log.e("Feed", it.message!!)
                    }
                }
            }
        })
        return inflate
    }

    /**
     * Récupération Fil D'actualité Offres
     */
    private fun feedOffres(
        listOffer: List<OffreResponseModel>?,
        recyclerView: RecyclerView,
        errorText: TextView
    ) {
        if (listOffer.isNullOrEmpty()) {
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            errorText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = FeedOfferAdapter(listOffer)
            adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        }
    }

    /**
     * Récupération Fil D'actualité Marques
     */
    private fun feedShop(
        listShop: List<ShopResponseModel>?,
        recyclerView: RecyclerView,
        errorText: TextView
    ) {
        if (listShop.isNullOrEmpty()) {
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            errorText.visibility =
                View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = FeedShopAdapter(listShop)
            adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        }
    }

    companion object {
        var influenceurData: InfluenceurResponseModel? = InfluenceurResponseModel()
    }
}
