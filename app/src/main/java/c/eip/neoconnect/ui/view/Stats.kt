package c.eip.neoconnect.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.StatsCommentAdapter
import c.eip.neoconnect.ui.adapter.StatsMarkAdapter
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class Stats : Fragment() {
    private lateinit var viewModel: InfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_stats, container, false)
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.statsLayout)
                .setBackgroundResource(R.drawable.background_shop)
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.statsLayout)
                .setBackgroundResource(R.drawable.background_influencer)
        }
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        viewModel.getProfilInf(token!!).observe(viewLifecycleOwner, Observer {
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
                        Log.e("Profil", it.message)
                        findNavController().popBackStack()
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