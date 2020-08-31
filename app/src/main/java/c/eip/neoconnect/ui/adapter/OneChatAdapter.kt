package c.eip.neoconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.ChannelDetail
import c.eip.neoconnect.data.model.message.OneChannelResponse

private const val MY_MESSAGE = 1
private const val OTHER_MESSAGE = 2

class OneChatAdapter(
    private val listMessage: OneChannelResponse,
    private val userId: Int
) : RecyclerView.Adapter<OneChatAdapter.ChatHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (listMessage.data[position].userId == userId.toString()) {
            MY_MESSAGE
        } else {
            OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = if (viewType == MY_MESSAGE) {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_message, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_other_message, parent, false)
        }
        return ChatHolder(view)
    }

    override fun getItemCount(): Int {
        return listMessage.data.size
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bindItems(listMessage.data[position])
    }

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(channel: ChannelDetail) {
            val type = itemViewType
            if (type == 1) {
                itemView.findViewById<TextView>(R.id.messageTxt).text = channel.message
            } else {
                itemView.findViewById<TextView>(R.id.otherMessageTxt).text = channel.message
            }
        }
    }
}