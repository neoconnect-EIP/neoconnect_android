package c.eip.neoconnect.ui.view.stats

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class Stats : Fragment() {
    private lateinit var infViewModel: InfViewModel
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var infData: InfluenceurResponseModel

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
                    "me" -> {
                        getStatsInf(inflate = inflate, id = 0)
                        Handler().postDelayed({
                            if (infData.countParrainage!! >= 5) {
                                inflate.findViewById<Button>(R.id.advancedStats).visibility =
                                    View.VISIBLE
                            } else {
                                inflate.findViewById<Button>(R.id.advancedStats).visibility =
                                    View.GONE
                            }
                        }, 1000)
                    }
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
        view.findViewById<Button>(R.id.advancedStats).setOnClickListener {
            val mDialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_advanced_stats, null)
            val mAlertDialogBuilder =
                AlertDialog.Builder(requireContext()).setView(mDialogView)
            mAlertDialogBuilder.show()
            mDialogView.findViewById<Button>(R.id.graphInstagram).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "instagram")
            }
            mDialogView.findViewById<Button>(R.id.graphPinterest).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "pinterest")
            }
            mDialogView.findViewById<Button>(R.id.graphTiktok).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "tiktok")
            }
            mDialogView.findViewById<Button>(R.id.graphTwitch).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "twitch")
            }
            mDialogView.findViewById<Button>(R.id.graphTwitter).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "twitter")
            }
            mDialogView.findViewById<Button>(R.id.graphYoutube).setOnClickListener {
                mDialogView.findViewById<LineChart>(R.id.lineChart).visibility = View.GONE
                displayGraph(mDialogView, "youtube")
            }
        }
    }

    /**
     * Affichage graphique
     */
    private fun displayGraph(view: View, network: String) {
        val entries = ArrayList<Entry>()
        val labelXaxis = ArrayList<String>()
        var leftAxisMax = 30000f
        var xAxisMax = 10f
        var checkList = true
        when (network) {
            "instagram" -> {
                for (i in infData.instagramNb!!.indices) {
                    if (infData.instagramNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.instagramNb!![i].toFloat()))
                    }
                }
                for (i in infData.instagramUpdateDate!!.indices) {
                    labelXaxis.add(infData.instagramUpdateDate!![i])
                }
                if (infData.instagramNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.instagramNb!![infData.instagramNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.instagramNb!![infData.instagramNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.instagramNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
            "pinterest" -> {
                for (i in infData.pinterestNb!!.indices) {
                    if (infData.pinterestNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.pinterestNb!![i].toFloat()))
                    }
                }
                for (i in infData.pinterestUpdateDate!!.indices) {
                    labelXaxis.add(infData.pinterestUpdateDate!![i])
                }
                if (infData.pinterestNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.pinterestNb!![infData.pinterestNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.pinterestNb!![infData.pinterestNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.pinterestNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
            "tiktok" -> {
                for (i in infData.tiktokNb!!.indices) {
                    if (infData.tiktokNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.tiktokNb!![i].toFloat()))
                    }
                }
                for (i in infData.tiktokUpdateDate!!.indices) {
                    labelXaxis.add(infData.tiktokUpdateDate!![i])
                }
                if (infData.tiktokNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.tiktokNb!![infData.tiktokNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.tiktokNb!![infData.tiktokNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.tiktokNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
            "twitch" -> {
                for (i in infData.twitchNb!!.indices) {
                    if (infData.twitchNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.twitchNb!![i].toFloat()))
                    }
                }
                for (i in infData.twitchUpdateDate!!.indices) {
                    labelXaxis.add(infData.twitchUpdateDate!![i])
                }
                if (infData.twitchNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.twitchNb!![infData.twitchNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.twitchNb!![infData.twitchNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.twitchNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
            "twitter" -> {
                for (i in infData.twitterNb!!.indices) {
                    if (infData.twitterNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.twitterNb!![i].toFloat()))
                    }
                }
                for (i in infData.twitterUpdateDate!!.indices) {
                    labelXaxis.add(infData.twitterUpdateDate!![i])
                }
                if (infData.twitterNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.twitterNb!![infData.twitterNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.twitterNb!![infData.twitterNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.twitterNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
            "youtube" -> {
                for (i in infData.youtubeNb!!.indices) {
                    if (infData.youtubeNb?.getOrNull(i) != null) {
                        entries.add(Entry(i.toFloat(), infData.youtubeNb!![i].toFloat()))
                    }
                }
                for (i in infData.youtubeUpdateDate!!.indices) {
                    labelXaxis.add(infData.youtubeUpdateDate!![i])
                }
                if (infData.youtubeNb!!.getOrNull(0) != null) {
                    var add = 30000
                    when (infData.youtubeNb!![infData.youtubeNb!!.size - 1]) {
                        in 0..9999 -> add = 3000
                        in 10000..99999 -> add = 30000
                        in 100000..999999 -> add = 300000
                        in 1000000..9999999 -> add = 3000000
                        in 10000000..99999999 -> add = 30000000
                        in 100000000..999999999 -> add = 300000000
                    }
                    leftAxisMax =
                        (infData.youtubeNb!![infData.youtubeNb!!.size - 1] + add).toFloat()
                    xAxisMax = (infData.youtubeNb!!.size + 1).toFloat()
                } else {
                    checkList = false
                }
            }
        }

        val chart = view.findViewById<LineChart>(R.id.lineChart)
        if (checkList) {
            val line = LineDataSet(entries, network.capitalize())
            view.findViewById<TextView>(R.id.pb_stats_advanced).visibility = View.GONE
            line.setCircleColor(R.color.colorPrimaryDark)
            line.lineWidth = 2f
            line.setDrawFilled(true)
            chart.data = LineData(line)
            chart.visibility = View.VISIBLE
            chart.setNoDataText("Non disponible")
            chart.setPinchZoom(true)
            chart.description = null
            chart.axisRight.setDrawLabels(false)
            chart.axisLeft.axisMinimum = 0f
            chart.axisLeft.axisMaximum = leftAxisMax
            chart.axisLeft.setDrawGridLines(false)
            chart.axisRight.setDrawGridLines(false)
            chart.xAxis.setDrawGridLines(false)
            chart.xAxis.axisMinimum = 0f
            chart.xAxis.axisMaximum = xAxisMax
            chart.xAxis.granularity = 2f
            chart.xAxis.labelCount = 5
            chart.data.setValueFormatter(object: ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "" + value.toInt()
                }
            })
            chart.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    if (value >= 0) {
                        if (value <= labelXaxis.size - 1) {
                            return labelXaxis[value.toInt()]
                        }
                        return ""
                    }
                    return ""
                }
            }
        } else {
            view.findViewById<TextView>(R.id.pb_stats_advanced).visibility = View.VISIBLE
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
                        infData = it.data!!
                        inflate.findViewById<RatingBar>(R.id.statsRatingBar).rating =
                            it.data.average!!.toFloat()
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