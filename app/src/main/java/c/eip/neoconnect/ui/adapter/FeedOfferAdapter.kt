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
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Adapter pour Fil d'actualité Offres
 */
class FeedOfferAdapter(private val offers: List<OffreResponseModel>) :
    RecyclerView.Adapter<FeedOfferAdapter.OfferHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_feed, parent, false)
        return OfferHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return offers.size
    }

    /**
     * Liaison entre les éléments de la liste offers et le layout
     */
    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        holder.bindItems(offers[position])
    }

    inner class OfferHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(offer: OffreResponseModel) {
            if (offer.productImg.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.feedCardPicture)
                    .setImageResource(R.drawable.ic_picture_offer)
            } else {
                Glide.with(itemView.context).load(offer.productImg[0].imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.feedCardPicture))
            }
            itemView.findViewById<TextView>(R.id.feedCardName).text = offer.productName
            itemView.findViewById<TextView>(R.id.feedCardSubject).text = offer.productSubject
            if (offer.brand.isNullOrEmpty()) {
                itemView.findViewById<TextView>(R.id.feedCardExtra).text =
                    itemView.context.getString(R.string.notDefined)
            } else {
                itemView.findViewById<TextView>(R.id.feedCardExtra).text = offer.brand
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("idOffer" to offer.id, "idUser" to offer.idUser)
                itemView.findNavController().navigate(R.id.navigation_offer_info, bundle)
            }
        }
    }
}