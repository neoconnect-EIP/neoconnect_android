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
 * Adapter pour la Liste des Offres
 */
class OfferAdapter(private val offers: ArrayList<OffreResponseModel>, private val type: String) :
    RecyclerView.Adapter<OfferAdapter.OfferHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_offer, parent, false)
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
                itemView.findViewById<ImageView>(R.id.offerPicture)
                    .setImageResource(R.drawable.ic_picture_offer)
            } else {
                Glide.with(itemView.context).load(offer.productImg[0].imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.offerPicture))
            }
            itemView.findViewById<TextView>(R.id.offerName).text = offer.productName
            itemView.findViewById<TextView>(R.id.offerSubject).text = offer.productSubject

            itemView.setOnClickListener {
                val bundle = bundleOf("idUser" to offer.idUser)
                if (type == "applied") {
                    bundle.putInt("idOffer", offer.idOffer)
                    bundle.putString("status", offer.status)
                } else {
                    bundle.putInt("idOffer", offer.id)
                }
                itemView.findNavController().navigate(R.id.navigation_offer_info, bundle)
            }
        }
    }
}