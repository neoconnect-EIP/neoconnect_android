package c.eip.neoconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.mark.MarkModel

/**
 * Adapter pour Liste des Notes
 */
class StatsMarkAdapter(
    private val listMark: List<MarkModel>
) :
    RecyclerView.Adapter<StatsMarkAdapter.StatsHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsMarkAdapter.StatsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_marks, parent, false)
        return StatsHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return listMark.size
    }

    override fun onBindViewHolder(holder: StatsHolder, position: Int) {
        holder.bindItems(listMark[position])
    }

    inner class StatsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(mark: MarkModel) {
            itemView.findViewById<RatingBar>(R.id.markElement).rating = mark.mark!!.toFloat()
        }

    }
}