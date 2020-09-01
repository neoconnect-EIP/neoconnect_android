package c.eip.neoconnect.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreApply
import c.eip.neoconnect.data.model.offres.OffreApplyUserResponseModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class OfferApplyUserAdapter(
    private val users: ArrayList<OffreApplyUserResponseModel>,
    private val viewModel: OffresViewModel, private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<OfferApplyUserAdapter.OfferApplyUserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferApplyUserHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_offer_apply_user, parent, false)
        return OfferApplyUserHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: OfferApplyUserHolder, position: Int) {
        holder.bindItems(users[position])
    }

    inner class OfferApplyUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: OffreApplyUserResponseModel) {
            if (user.userPicture.isNullOrEmpty()) {
                itemView.findViewById<ImageView>(R.id.userPicture)
                    .setImageResource(R.drawable.ic_picture_inf)
            } else {
                Glide.with(itemView.context).load(user.userPicture[0].imageData).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(R.drawable.ic_picture_shop)
                    .into(itemView.findViewById(R.id.userPicture))
            }
            itemView.findViewById<TextView>(R.id.userPseudo).text = user.pseudo
            itemView.findViewById<TextView>(R.id.userTheme).text = user.theme
            if (user.average.isNullOrEmpty()) {
                itemView.findViewById<TextView>(R.id.userAverage).text = "0 ★"
            } else {
                val average = String.format("%.2f", user.average!!.toFloat()) + " ★"
                itemView.findViewById<TextView>(R.id.userAverage).text = average
            }
            itemView.findViewById<Button>(R.id.acceptApplyButton).setOnClickListener {
                val choice = OffreApply()
                choice.idUser = user.idUser
                choice.idOffer = user.idOffer
                choice.status = true
                val token = DataGetter.INSTANCE.getToken(itemView.context)
                viewModel.choiceApply(token!!, choice).observe(lifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(itemView.context, it.message, Toast.LENGTH_SHORT)
                                    .show()
                                Log.i("Choice Apply", it.message)
                            }
                            Status.ERROR -> {
                                Toast.makeText(itemView.context, it.message, Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("Choice Apply", it.message)
                            }
                        }
                    }
                })
            }
            itemView.findViewById<Button>(R.id.declineApplyButton).setOnClickListener {
                val choice = OffreApply()
                choice.idUser = user.idUser
                choice.idOffer = user.idOffer
                choice.status = false
                val token = DataGetter.INSTANCE.getToken(itemView.context)
                viewModel.choiceApply(token!!, choice).observe(lifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(itemView.context, it.message, Toast.LENGTH_SHORT)
                                    .show()
                                Log.i("Choice Apply", it.message)
                            }
                            Status.ERROR -> {
                                Toast.makeText(itemView.context, it.message, Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("Choice Apply", it.message)
                            }
                        }
                    }
                })
            }
        }
    }
}