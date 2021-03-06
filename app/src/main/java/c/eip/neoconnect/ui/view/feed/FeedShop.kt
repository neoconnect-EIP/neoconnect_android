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
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.ui.adapter.FeedInfAdapter
import c.eip.neoconnect.ui.viewModel.FeedViewModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class FeedShop : Fragment() {
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var shopViewModel: ShopViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_feed_shop, container, false)
        val token = DataGetter.INSTANCE.getToken(requireContext())
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        shopViewModel.getProfilShop(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        shopData = it.data
                        val title = "Bonjour, ${shopData?.pseudo}"
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
                        feedInf(
                            listInf = it.data?.listInfTendance,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListInfTendances),
                            errorText = inflate.findViewById(R.id.pb_feed_inf_tendance)
                        )

                        feedInf(
                            listInf = it.data?.listInfPopulaire,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListInfPopulaires),
                            errorText = inflate.findViewById(R.id.pb_feed_inf_populaire)
                        )

                        feedInf(
                            listInf = it.data?.listInfNotes,
                            recyclerView = inflate.findViewById(R.id.recyclerFeedListInfNotes),
                            errorText = inflate.findViewById(R.id.pb_feed_inf_notes)
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
     * Récupération Fil D'actualité Influenceurs
     */
    private fun feedInf(
        listInf: List<InfluenceurResponseModel>?,
        recyclerView: RecyclerView,
        errorText: TextView
    ) {
        if (listInf.isNullOrEmpty()) {
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            errorText.visibility =
                View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = FeedInfAdapter(listInf)
            adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        }
    }

    companion object {
        var shopData: ShopResponseModel? = ShopResponseModel()
    }
}
