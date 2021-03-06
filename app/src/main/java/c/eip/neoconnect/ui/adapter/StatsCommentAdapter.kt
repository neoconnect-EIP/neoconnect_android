package c.eip.neoconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.comment.CommentModel

/**
 * Adapter pour Liste des commentaires
 */
class StatsCommentAdapter(
    private val listComment: List<CommentModel>
) :
    RecyclerView.Adapter<StatsCommentAdapter.StatsHolder>() {

    /**
     * Déclaration du layout des éléments qui s'afficheront
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsCommentAdapter.StatsHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_comments, parent, false)
        return StatsHolder(view)
    }

    /**
     * Récupération du nombre d'élément de la liste
     */
    override fun getItemCount(): Int {
        return listComment.size
    }

    override fun onBindViewHolder(holder: StatsHolder, position: Int) {
        holder.bindItems(listComment[position])
    }

    inner class StatsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(comment: CommentModel) {
            itemView.findViewById<TextView>(R.id.commentElement).text = comment.comment
        }

    }
}