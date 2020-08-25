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
                        val recyclerOffresPopulairesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresPopulaires)
                        recyclerOffresPopulairesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val offresPopulairesAdapter =
                            FeedOfferAdapter(it.data?.listOffrePopulaires!!)
                        offresPopulairesAdapter.notifyDataSetChanged()
                        recyclerOffresPopulairesView.adapter = offresPopulairesAdapter
                        val recyclerOffresTendancesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresTendances)
                        recyclerOffresTendancesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val offresTendancesAdapter = FeedOfferAdapter(it.data.listOffreTendances)
                        offresTendancesAdapter.notifyDataSetChanged()
                        recyclerOffresTendancesView.adapter = offresTendancesAdapter
                        val recyclerOffresNotesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListOffresNotes)
                        recyclerOffresNotesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val offresNotesAdapter = FeedOfferAdapter(it.data.listOffreNotes)
                        offresNotesAdapter.notifyDataSetChanged()
                        recyclerOffresNotesView.adapter = offresNotesAdapter

                        val recyclerShopPopulairesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopPopulaires)
                        recyclerShopPopulairesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val shopPopulairesAdapter = FeedShopAdapter(it.data.listShopPopulaires)
                        shopPopulairesAdapter.notifyDataSetChanged()
                        recyclerShopPopulairesView.adapter = shopPopulairesAdapter
                        val recyclerShopTendancesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopTendances)
                        recyclerShopTendancesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val shopTendancesAdapter = FeedShopAdapter(it.data.listShopTendances)
                        shopTendancesAdapter.notifyDataSetChanged()
                        recyclerShopTendancesView.adapter = shopTendancesAdapter
                        val recyclerShopNotesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListShopNotes)
                        recyclerShopNotesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val shopNotesAdapter = FeedShopAdapter(it.data.listShopNotes)
                        shopNotesAdapter.notifyDataSetChanged()
                        recyclerShopNotesView.adapter = shopNotesAdapter
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
