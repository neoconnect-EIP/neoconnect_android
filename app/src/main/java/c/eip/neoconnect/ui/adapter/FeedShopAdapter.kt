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
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Adapter pour Fil d'actualité Marque
 */
class FeedShopAdapter(private val listShop: List<ShopResponseModel>) :
    RecyclerView.Adapter<FeedShopAdapter.ShopHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_feed, parent, false)
        return ShopHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return listShop.size
    }

    /**
     * Liaison entre les éléments de la liste listShop et le layout
     */
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        holder.bindItems(listShop[position])
    }

    inner class ShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(shop: ShopResponseModel) {
            val feedCardPicture = itemView.findViewById<ImageView>(R.id.feedCardPicture)
            feedCardPicture.maxHeight = 125
            feedCardPicture.maxWidth = 125
            if (shop.userPicture.isNullOrEmpty()) {
                feedCardPicture.setImageResource(R.drawable.ic_picture_shop)
            } else {
                Glide.with(itemView.context).load(shop.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(feedCardPicture)
            }
            itemView.findViewById<TextView>(R.id.feedCardName).text = shop.pseudo
            itemView.findViewById<TextView>(R.id.feedCardSubject).text = shop.theme
            if (shop.average != null && shop.mark.isNotEmpty()) {
                val average = String.format("%.2f", shop.average!!.toFloat())
                val extra = "$average★ (${shop.mark.size})"
                itemView.findViewById<TextView>(R.id.feedCardExtra).text = extra
            } else {
                val extra = "0 ★ (0)"
                itemView.findViewById<TextView>(R.id.feedCardExtra).text = extra
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to shop.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_shop, bundle)
            }
        }
    }
}