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
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.FeedInfAdapter
import c.eip.neoconnect.ui.viewModel.FeedViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class FeedShop : Fragment() {
    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_feed_shop, container, false)
        val title = "Bonjour, ${MainViewShop.shopData?.pseudo}"
        inflate.findViewById<TextView>(R.id.titleFeed).text = title
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.getFeed(token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val recyclerInfPopulairesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListInfPopulaires)
                        recyclerInfPopulairesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val infPopulairesAdapter = FeedInfAdapter(it.data?.listInfPopulaires!!)
                        infPopulairesAdapter.notifyDataSetChanged()
                        recyclerInfPopulairesView.adapter = infPopulairesAdapter
                        val recyclerInfTendancesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListInfTendances)
                        recyclerInfTendancesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val infTendancesAdapter = FeedInfAdapter(it.data.listInfTendances)
                        infTendancesAdapter.notifyDataSetChanged()
                        recyclerInfTendancesView.adapter = infTendancesAdapter
                        val recyclerInfNotesView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerFeedListInfNotes)
                        recyclerInfNotesView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val infNotesAdapter = FeedInfAdapter(it.data.listInfNotes)
                        infNotesAdapter.notifyDataSetChanged()
                        recyclerInfNotesView.adapter = infNotesAdapter
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
