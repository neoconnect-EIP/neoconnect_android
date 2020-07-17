package c.eip.neoconnect.ui.view.offer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class OfferInfo : Fragment() {
    private lateinit var viewModel: OffresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_offer_info, container, false)
        if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_shop)
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.VISIBLE
        } else if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.GONE
        }
        val offerId = arguments?.get("idOffer") as Int
        val userId = arguments?.get("idUser") as Int
        val token = DataGetter.INSTANCE.getToken(context!!)
        if (DataGetter.INSTANCE.getUserType(context!!) == "shop"
            && DataGetter.INSTANCE.getUserId(context!!) != userId
        ) {
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.GONE
        }
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModel.getOneOffer(token!!, offerId).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.productImg.isNullOrEmpty()) {
                            inflate.findViewById<ImageView>(R.id.infoOfferPicture1)
                                .setImageResource(R.drawable.ic_picture_offer)
                            inflate.findViewById<ImageView>(R.id.infoOfferPicture2).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.infoOfferPicture3).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.infoOfferPicture4).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.infoOfferPicture5).visibility =
                                View.GONE
                        } else {
                            Glide.with(context!!).load(it.data!!.productImg[0].imageData)
                                .fitCenter().error(R.drawable.ic_picture_offer)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(inflate.findViewById(R.id.offerPicture))
                        }
                        inflate.findViewById<TextView>(R.id.offerName).text = it.data?.productName
                        inflate.findViewById<TextView>(R.id.offerSex).text = it.data?.productSex
                        inflate.findViewById<TextView>(R.id.offerDescrption).text =
                            it.data?.productDesc
                        inflate.findViewById<TextView>(R.id.offerColor).text = it.data?.color
                        inflate.findViewById<TextView>(R.id.offerSubject).text =
                            it.data?.productSubject
                        inflate.findViewById<TextView>(R.id.offerAverage).text = it.data?.average
                    }
                    Status.ERROR -> {
                        Log.e("Get Offer", "$offerId introuvable")
                        Toast.makeText(
                            context,
                            "L'offre recherché est introuvable",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        })
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val offerId = arguments?.get("idOffer") as Int
        val token = DataGetter.INSTANCE.getToken(context!!)
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.removeOfferButton).setOnClickListener {
            viewModel.deleteOffer(token!!, offerId).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Delete Offer", it.message)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Delete Offer", it.message)
                        }
                    }
                }
            })
        }

        view.findViewById<Button>(R.id.applyOfferButton).setOnClickListener {
            viewModel.applyOffer(token!!, offerId).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Apply Offer", it.message)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Apply Offer", it.message)
                        }
                    }
                }
            })
        }
    }
}
