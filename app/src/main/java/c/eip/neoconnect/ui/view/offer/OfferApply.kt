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

class OfferApply : Fragment() {
    private lateinit var viewModelOffer: OffresViewModel
    private lateinit var viewModelList: ListViewModel
    private var offre = OfferInfo.offreReponse

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_offer_apply, container, false)
        val token = DataGetter.INSTANCE.getToken(requireContext())

        if (offre != null) {
            inflate.findViewById<TextView>(R.id.offerName).text = offre?.productName
            if (offre?.productImg.isNullOrEmpty()) {
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
                Glide.with(requireContext()).load(offre?.productImg?.get(0)?.imageData)
                    .fitCenter().error(R.drawable.ic_picture_offer)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(inflate.findViewById(R.id.infoOfferPicture1))
            }
            val recyclerView = inflate.findViewById<RecyclerView>(R.id.recyclerListOfferApplyUser)
            if (arguments?.get("offerId") != null) {
                getOfferApplyUser(
                    token = token!!,
                    offerId = arguments?.get("offerId") as Int,
                    recyclerView = recyclerView
                )
            }
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Récupération de la liste des personnes ayant postulés à cette offre
     */
    private fun getOfferApplyUser(token: String, offerId: Int, recyclerView: RecyclerView) {
        viewModelList = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModelList.getOfferApplyUser(token = token, id = offerId)
            .observe(viewLifecycleOwner, Observer { itList ->
                itList?.let { resourceList ->
                    when (resourceList.status) {
                        Status.SUCCESS -> {
                            recyclerView.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            viewModelOffer =
                                ViewModelProvider(this).get(OffresViewModel::class.java)
                            val adapter = OfferApplyUserAdapter(
                                itList.data!!,
                                viewModelOffer,
                                this
                            )
                            adapter.notifyDataSetChanged()
                            recyclerView.adapter = adapter
                            Log.i(
                                "Get Offer Apply User",
                                itList.data.size.toString()
                            )
                        }
                        Status.ERROR -> {
                            Log.e("Get Offer Apply User", itList.message!!)
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
}