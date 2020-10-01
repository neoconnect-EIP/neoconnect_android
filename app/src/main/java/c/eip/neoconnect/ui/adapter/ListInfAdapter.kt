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
 * Adapter pour la Liste des Influenceurs
 */
class ListInfAdapter(private val listInf: ArrayList<InfluenceurResponseModel>) :
    RecyclerView.Adapter<ListInfAdapter.ListInfHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListInfHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_user, parent, false)
        return ListInfHolder(view)
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
    override fun onBindViewHolder(holder: ListInfHolder, position: Int) {
        holder.bindItems(listInf[position])
    }

    inner class ListInfHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(inf: InfluenceurResponseModel) {
            if (inf.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.userPicture)
                    .setImageResource(R.drawable.ic_picture_inf)
            } else {
                Glide.with(itemView.context).load(inf.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_inf)
                    .into(itemView.findViewById(R.id.userPicture))
            }
            itemView.findViewById<TextView>(R.id.userPseudo).text = inf.pseudo
            itemView.setOnClickListener {
                val bundle = bundleOf("mode" to 0, "id" to inf.id)
                itemView.findNavController().navigate(R.id.navigation_other_profil_inf, bundle)
            }
        }
    }


}