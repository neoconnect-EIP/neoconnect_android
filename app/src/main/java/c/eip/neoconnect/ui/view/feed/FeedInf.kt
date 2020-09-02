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
import c.eip.neoconnect.MainViewInf
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.FeedOfferAdapter
import c.eip.neoconnect.ui.adapter.FeedShopAdapter
import c.eip.neoconnect.ui.viewModel.FeedViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class FeedInf : Fragment() {
    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_feed_inf, container, false)
        val title = "Bonjour, ${MainViewInf.influenceurData?.pseudo}"
        inflate.findViewById<TextView>(R.id.titleFeed).text = title

        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.getFeed(token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.listOfferTendance.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_tendance).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresTendances).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_tendance).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresTendances).visibility =
                                View.VISIBLE
                            val recyclerOffresTendancesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresTendances)
                            recyclerOffresTendancesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val offresTendancesAdapter =
                                FeedOfferAdapter(it.data?.listOfferTendance!!)
                            offresTendancesAdapter.notifyDataSetChanged()
                            recyclerOffresTendancesView.adapter = offresTendancesAdapter
                        }

                        if (it.data?.listOfferPopulaire.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_populaire).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresPopulaires).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_populaire).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresPopulaires).visibility =
                                View.VISIBLE
                            val recyclerOffresPopulairesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresPopulaires)
                            recyclerOffresPopulairesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val offresPopulairesAdapter =
                                FeedOfferAdapter(it.data?.listOfferPopulaire!!)
                            offresPopulairesAdapter.notifyDataSetChanged()
                            recyclerOffresPopulairesView.adapter = offresPopulairesAdapter
                        }

                        if (it.data?.listOfferNotes.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_notes).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresNotes).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_offer_notes).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresNotes).visibility =
                                View.VISIBLE
                            val recyclerOffresNotesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresNotes)
                            recyclerOffresNotesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val offresNotesAdapter = FeedOfferAdapter(it.data?.listOfferNotes!!)
                            offresNotesAdapter.notifyDataSetChanged()
                            recyclerOffresNotesView.adapter = offresNotesAdapter
                        }

                        if (it.data?.listShopTendance.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_tendance).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopTendances).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_tendance).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopTendances).visibility =
                                View.VISIBLE
                            val recyclerShopTendancesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopTendances)
                            recyclerShopTendancesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val shopTendancesAdapter = FeedShopAdapter(it.data?.listShopTendance!!)
                            shopTendancesAdapter.notifyDataSetChanged()
                            recyclerShopTendancesView.adapter = shopTendancesAdapter
                        }

                        if (it.data?.listShopPopulaire.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_populaire).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopPopulaires).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_populaire).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopPopulaires).visibility =
                                View.VISIBLE
                            val recyclerShopPopulairesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopPopulaires)
                            recyclerShopPopulairesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val shopPopulairesAdapter =
                                FeedShopAdapter(it.data?.listShopPopulaire!!)
                            shopPopulairesAdapter.notifyDataSetChanged()
                            recyclerShopPopulairesView.adapter = shopPopulairesAdapter
                        }

                        if (it.data?.listShopNotes.isNullOrEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_notes).visibility =
                                View.VISIBLE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopNotes).visibility =
                                View.GONE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_feed_shop_notes).visibility =
                                View.GONE
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopNotes).visibility =
                                View.VISIBLE
                            val recyclerShopNotesView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopNotes)
                            recyclerShopNotesView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val shopNotesAdapter = FeedShopAdapter(it.data?.listShopNotes!!)
                            shopNotesAdapter.notifyDataSetChanged()
                            recyclerShopNotesView.adapter = shopNotesAdapter
                        }
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_feed).visibility = View.VISIBLE
                        inflate.findViewById<ScrollView>(R.id.feedLayout).visibility = View.GONE
                        Log.e("Feed", it.message)
                    }
                }
            }
        })
        return inflate
    }
}
