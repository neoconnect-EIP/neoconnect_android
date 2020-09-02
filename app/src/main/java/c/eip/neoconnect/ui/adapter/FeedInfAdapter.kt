package c.eip.neoconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FeedInfAdapter(private val listInf: List<InfluenceurResponseModel>) :
    RecyclerView.Adapter<FeedInfAdapter.InfHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_feed, parent, false)
        return InfHolder(view)
    }

    override fun getItemCount(): Int {
        return listInf.size
    }

    override fun onBindViewHolder(holder: InfHolder, position: Int) {
        holder.bindItems(listInf[position])
    }

    inner class InfHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(inf: InfluenceurResponseModel) {
            if (inf.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.feedCardPicture)
                    .setImageResource(R.drawable.ic_picture_inf)
            } else {
                Glide.with(itemView.context).load(inf.userPicture[0]?.imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_inf)
                    .into(itemView.findViewById(R.id.feedCardPicture))
            }
            itemView.findViewById<TextView>(R.id.feedCardName).text = inf.pseudo
            itemView.findViewById<TextView>(R.id.feedCardSubject).text = inf.theme
            if (inf.average.isNullOrEmpty()) {
                itemView.findViewById<TextView>(R.id.feedCardAverage).text = "0 ★"
            } else {
                val average = String.format("%.1f", inf.average!!.toFloat()) + " ★"
                itemView.findViewById<TextView>(R.id.feedCardAverage).text = average
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to inf.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_inf, bundle)
            }
        }
    }
}