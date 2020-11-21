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
 * Adapter pour la Liste des Offres
 */
class ShopSuggestionAdapter(
    private val shops: ArrayList<ShopResponseModel>
) :
    RecyclerView.Adapter<ShopSuggestionAdapter.ShopHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_suggestion, parent, false)
        return ShopHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return shops.size
    }

    /**
     * Liaison entre les éléments de la liste offers et le layout
     */
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        holder.bindItems(shops[position])
    }

    inner class ShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(shop: ShopResponseModel) {
            val picture = itemView.findViewById<ImageView>(R.id.picture)
            picture.setPadding(0, 0, 0, 0)
            if (shop.userPicture.isNullOrEmpty()) {
                picture.setImageResource(R.drawable.ic_picture_shop)
            } else {
                Glide.with(itemView.context).load(shop.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(picture)
            }
            val name = itemView.findViewById<TextView>(R.id.name)
            name.setLines(1)
            name.text = shop.pseudo
            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to shop.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_shop, bundle)
            }
        }
    }
}