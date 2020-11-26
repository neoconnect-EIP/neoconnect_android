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

/**
 * Adapter pour Fil d'actualité Influenceur
 */
class FeedInfAdapter(private val listInf: List<InfluenceurResponseModel>) :
    RecyclerView.Adapter<FeedInfAdapter.InfHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_feed, parent, false)
        return InfHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return listInf.size
    }

    /**
     * Liaison entre les éléments de la liste listInf et le layout
     */
    override fun onBindViewHolder(holder: InfHolder, position: Int) {
        holder.bindItems(listInf[position])
    }

    inner class InfHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(inf: InfluenceurResponseModel) {
            val feedCardPicture = itemView.findViewById<ImageView>(R.id.feedCardPicture)
            feedCardPicture.maxHeight = 125
            feedCardPicture.maxWidth = 125
            if (inf.userPicture.isNullOrEmpty()) {
                feedCardPicture.setImageResource(R.drawable.ic_picture_inf)
            } else {
                Glide.with(itemView.context).load(inf.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_inf)
                    .into(feedCardPicture)
            }
            itemView.findViewById<TextView>(R.id.feedCardName).text = inf.pseudo
            itemView.findViewById<TextView>(R.id.feedCardSubject).text = inf.theme
            if (inf.average != null && inf.mark.isNotEmpty()) {
                val average = String.format("%.2f", inf.average!!.toFloat())
                val extra = "$average★ (${inf.mark.size})"
                itemView.findViewById<TextView>(R.id.feedCardExtra).text = extra
            } else {
                val extra = "0 ★ (0)"
                itemView.findViewById<TextView>(R.id.feedCardExtra).text = extra
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to inf.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_inf, bundle)
            }
        }
    }
}