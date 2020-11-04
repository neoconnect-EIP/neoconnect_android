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
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Adapter pour Fil d'actualité Boutique
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
            if (shop.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.feedCardPicture)
                    .setImageResource(R.drawable.ic_picture_shop)
            } else {
                Glide.with(itemView.context).load(shop.userPicture[0]?.imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.feedCardPicture))
            }
            itemView.findViewById<TextView>(R.id.feedCardName).text = shop.pseudo
            itemView.findViewById<TextView>(R.id.feedCardSubject).text = shop.theme
            itemView.findViewById<TextView>(R.id.feedCardShop).visibility = View.GONE
            itemView.findViewById<LinearLayout>(R.id.feedCardLayout).setPadding(20, 20, 20, 20)

            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to shop.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_shop, bundle)
            }
        }
    }
}