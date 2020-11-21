package c.eip.neoconnect.ui.view.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.ui.adapter.StatsCommentAdapter
import c.eip.neoconnect.ui.adapter.StatsMarkAdapter
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Resource
import c.eip.neoconnect.utils.Status

class Stats : Fragment() {
    private lateinit var infViewModel: InfViewModel
    private lateinit var shopViewModel: ShopViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Mise en place du fond selon Influenceur ou Marque
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_stats, container, false)
        val userType = DataGetter.INSTANCE.getUserType(requireContext())
        if (userType == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.statsLayout)
                .setBackgroundResource(R.drawable.background_shop)
            if (arguments?.get("profil") != null) {
                when (arguments?.get("profil")) {
                    "other" -> getStatsInf(inflate = inflate, id = arguments?.get("id") as Int)
                    "me" -> getStatsShop(inflate = inflate, id = 0)
                }
            }
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.statsLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            if (arguments?.get("profil") != null) {
                when (arguments?.get("profil")) {
                    "other" -> getStatsShop(inflate = inflate, id = arguments?.get("id") as Int)
                    "me" -> getStatsInf(inflate = inflate, id = 0)
                }
            }
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
     * Récupération des stats d'un compte Inf
     */
    private fun getStatsInf(inflate: View, id: Int) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        infViewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        val request: LiveData<Resource<InfluenceurResponseModel>>
        request = if (id == 0) {
            infViewModel.getProfilInf(token = token!!)
        } else {
            infViewModel.getOtherInf(token = token!!, id = id)
        }
        request.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        inflate.findViewById<RatingBar>(R.id.statsRatingBar).rating =
                            it.data?.average!!.toFloat()
                        val count = "sur " + it.data.mark.size + " évalutation(s)"
                        inflate.findViewById<TextView>(R.id.statsNumberCount).text = count
                        val recyclerStatsMarkView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerListMark)
                        recyclerStatsMarkView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        val markAdapter = StatsMarkAdapter(it.data.mark)
                        markAdapter.notifyDataSetChanged()
                        recyclerStatsMarkView.adapter = markAdapter

                        val recyclerStatsCommentView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerListComment)
                        recyclerStatsCommentView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        val commentAdapter = StatsCommentAdapter(it.data.comment)
                        commentAdapter.notifyDataSetChanged()
                        recyclerStatsCommentView.adapter = commentAdapter
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Profil", it.message!!)
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }

    /**
     * Récupération des stats d'un compte Shop
     */
    private fun getStatsShop(inflate: View, id: Int) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        val request: LiveData<Resource<ShopResponseModel>>
        request = if (id == 0) {
            shopViewModel.getProfilShop(token = token!!)
        } else {
            shopViewModel.getOtherShop(token = token!!, id = id)
        }
        request.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        inflate.findViewById<RatingBar>(R.id.statsRatingBar).rating =
                            it.data?.average!!.toFloat()
                        val count = "sur " + it.data.mark.size + " évalutation(s)"
                        inflate.findViewById<TextView>(R.id.statsNumberCount).text = count
                        val recyclerStatsMarkView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerListMark)
                        recyclerStatsMarkView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        val markAdapter = StatsMarkAdapter(it.data.mark)
                        markAdapter.notifyDataSetChanged()
                        recyclerStatsMarkView.adapter = markAdapter

                        val recyclerStatsCommentView =
                            inflate.findViewById<RecyclerView>(R.id.recyclerListComment)
                        recyclerStatsCommentView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        val commentAdapter = StatsCommentAdapter(it.data.comment)
                        commentAdapter.notifyDataSetChanged()
                        recyclerStatsCommentView.adapter = commentAdapter
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Profil", it.message!!)
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }
}