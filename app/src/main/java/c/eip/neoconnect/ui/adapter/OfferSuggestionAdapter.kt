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
class OfferSuggestionAdapter(
    private val offers: ArrayList<OffreResponseModel>,
    private val type: String
) :
    RecyclerView.Adapter<OfferSuggestionAdapter.OfferHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_suggestion, parent, false)
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
            val picture = itemView.findViewById<ImageView>(R.id.picture)
            picture.setPadding(10, 10, 10, 10)
            if (offer.productImg.isNullOrEmpty()) {
                picture.setImageResource(R.drawable.ic_picture_offer)
            } else {
                Glide.with(itemView.context).load(offer.productImg[0].imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(picture)
            }
            val name = itemView.findViewById<TextView>(R.id.name)
            name.setLines(2)
            name.text = offer.productName
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