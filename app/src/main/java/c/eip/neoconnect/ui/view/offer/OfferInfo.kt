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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.OfferApplyUserAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class OfferInfo : Fragment() {
    private lateinit var viewModelOffer: OffresViewModel
    private lateinit var viewModelList: ListViewModel
    private var getOfferState: Boolean = false
    private var getOfferApplyUserState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_offer_info, container, false)

        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_shop)
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.VISIBLE
            inflate.findViewById<RecyclerView>(R.id.recyclerListOfferApplyUser).visibility =
                View.VISIBLE
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.VISIBLE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.GONE
            inflate.findViewById<RecyclerView>(R.id.recyclerListOfferApplyUser).visibility =
                View.GONE
        }
        val offerId = arguments?.get("idOffer") as Int
        val userId = arguments?.get("idUser") as Int
        val token = DataGetter.INSTANCE.getToken(requireContext())
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop"
            && DataGetter.INSTANCE.getUserId(requireContext()) != userId
        ) {
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.reportOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.editOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.removeOfferButton).visibility = View.GONE
            inflate.findViewById<RecyclerView>(R.id.recyclerListOfferApplyUser).visibility =
                View.GONE
        }
        viewModelList = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.getOneOffer(token!!, offerId).observe(viewLifecycleOwner, Observer {
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
                            Glide.with(requireContext()).load(it.data!!.productImg[0].imageData)
                                .fitCenter().error(R.drawable.ic_picture_offer)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(inflate.findViewById(R.id.infoOfferPicture1))
                        }
                        inflate.findViewById<TextView>(R.id.offerName).text = it.data?.productName
                        inflate.findViewById<TextView>(R.id.offerSex).text = it.data?.productSex
                        inflate.findViewById<TextView>(R.id.offerDescrption).text =
                            it.data?.productDesc
                        inflate.findViewById<TextView>(R.id.offerColor).text = it.data?.color
                        inflate.findViewById<TextView>(R.id.offerSubject).text =
                            it.data?.productSubject
                        inflate.findViewById<TextView>(R.id.offerAverage).text = it.data?.average
                        getOfferState = true
                        viewModelList.getOfferApplyUser(token, offerId)
                            .observe(viewLifecycleOwner, Observer { itList ->
                                itList?.let { resourceList ->
                                    when (resourceList.status) {
                                        Status.SUCCESS -> {
                                            val recyclerListView =
                                                inflate.findViewById<RecyclerView>(R.id.recyclerListOfferApplyUser)
                                            recyclerListView.layoutManager =
                                                LinearLayoutManager(
                                                    context,
                                                    LinearLayoutManager.VERTICAL,
                                                    false
                                                )
                                            val adapter = OfferApplyUserAdapter(
                                                itList.data!!,
                                                viewModelOffer,
                                                this
                                            )
                                            adapter.notifyDataSetChanged()
                                            recyclerListView.adapter = adapter
                                            Log.i(
                                                "Get Offer Apply User",
                                                itList.data.size.toString()
                                            )
                                            getOfferApplyUserState = true
                                        }
                                        Status.ERROR -> {
                                            getOfferApplyUserState = false
                                            Log.e("Get Offer Apply User", itList.message)
                                            Toast.makeText(
                                                context,
                                                itList.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }

                                }
                            })
                    }
                    Status.ERROR -> {
                        getOfferState = false
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
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.removeOfferButton).setOnClickListener {
            viewModelOffer.deleteOffer(token!!, offerId).observe(viewLifecycleOwner, Observer {
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
            viewModelOffer.applyOffer(token!!, offerId).observe(viewLifecycleOwner, Observer {
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
        view.findViewById<Button>(R.id.editOfferButton).setOnClickListener {
            val bundle = bundleOf("offerId" to offerId)
            findNavController().navigate(R.id.navigation_edit_offer, bundle)
        }
        view.findViewById<Button>(R.id.markOfferButton).setOnClickListener {
            val bundle = bundleOf("offerId" to offerId)
            findNavController().navigate(R.id.navigation_mark_offer, bundle)
        }
    }
}
