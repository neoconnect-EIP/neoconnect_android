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
 * Adapter pour la Liste des Marques
 */
class ListShopAdapter(private val listShop: ArrayList<ShopResponseModel>) :
    RecyclerView.Adapter<ListShopAdapter.ListShopHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShopHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_user, parent, false)
        return ListShopHolder(view)
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
    override fun onBindViewHolder(holder: ListShopHolder, position: Int) {
        holder.bindItems(listShop[position])
    }

    inner class ListShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(shop: ShopResponseModel) {
            if (shop.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.userPicture)
                    .setImageResource(R.drawable.ic_picture_shop)
            } else {
                Glide.with(itemView.context).load(shop.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.userPicture))
            }
            itemView.findViewById<TextView>(R.id.userPseudo).text = shop.pseudo
            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to shop.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_shop, bundle)
            }
        }
    }


}