package c.eip.neoconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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
            itemView.findViewById<TextView>(R.id.feedCardShop).visibility = View.GONE
            itemView.findViewById<LinearLayout>(R.id.feedCardLayout).setPadding(20, 20, 20, 20)

            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to inf.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_inf, bundle)
            }
        }
    }
}