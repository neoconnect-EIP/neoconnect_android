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
import c.eip.neoconnect.data.model.message.AllChannelResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ChatAdapter(private val listChannel: List<AllChannelResponse>) :
    RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_channel, parent, false)
        return ChatHolder(view)
    }

    override fun getItemCount(): Int {
        return listChannel.size
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bindItems(listChannel[position])
    }

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(channel: AllChannelResponse) {
            if (channel.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.userPicture)
                    .setImageResource(R.drawable.ic_picture_shop)
            } else {
                Glide.with(itemView.context).load(channel.userPicture[0]?.imageData).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.userPicture))
            }
            itemView.findViewById<TextView>(R.id.userPseudo).text = channel.pseudo
            itemView.setOnClickListener {
                val bundle = bundleOf("id" to channel.id, "pseudo" to channel.pseudo)
                itemView.findNavController().navigate(R.id.navigation_message, bundle)
            }
        }
    }
}