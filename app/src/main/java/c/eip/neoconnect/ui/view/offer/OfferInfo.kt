package c.eip.neoconnect.ui.view.offer


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class OfferInfo : Fragment() {
    private lateinit var viewModelOffer: OffresViewModel
    private lateinit var viewModelList: ListViewModel
    private var getOfferState: Boolean = false
    private var name: String? = ""

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Affichage des éléments selon Influenceur ou Marque
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_offer_info, container, false)

        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_shop)
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.offerInfoLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            when (arguments?.getString("status")) {
                "accepted" -> {
                    inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.VISIBLE
                    inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
                }
                "refused" -> {
                    inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.GONE
                }
                "pending" -> {
                    inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.VISIBLE
                }
                else -> {
                    inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.VISIBLE
                    inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
                    inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.GONE
                }
            }
        }
        val userId = arguments?.get("idUser") as Int
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop"
            && DataGetter.INSTANCE.getUserId(requireContext()) != userId
        ) {
            inflate.findViewById<Button>(R.id.markOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.cancelOfferButton).visibility = View.GONE
            inflate.findViewById<Button>(R.id.applyOfferButton).visibility = View.GONE
        }
        getOneOffer(inflate = inflate)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val offerId = arguments?.get("idOffer") as Int
        val token = DataGetter.INSTANCE.getToken(requireContext())

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.applyOfferButton).setOnClickListener {
            applyOffer(token = token, offerId = offerId)
        }
        view.findViewById<Button>(R.id.cancelOfferButton).setOnClickListener {
            cancelApplyOffer(token = token, offerId = offerId)
        }
        view.findViewById<Button>(R.id.markOfferButton).setOnClickListener {
            val bundle = bundleOf("offerId" to offerId)
            findNavController().navigate(R.id.navigation_mark_offer, bundle)
        }
        view.findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            settingsOption(view = view)
        }
    }

    /**
     * Récupération d'une offre
     */
    private fun getOneOffer(inflate: View) {
        val offerId = arguments?.get("idOffer") as Int
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModelList = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.getOneOffer(token = token!!, id = offerId)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            offreReponse = it.data
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
                                when (it.data?.productImg!!.size) {
                                    1 -> {
                                        inflate.findViewById<HorizontalScrollView>(R.id.hScrollViewOfferInfo).visibility =
                                            View.GONE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture).visibility =
                                            View.VISIBLE
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[0].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture))
                                    }
                                    2 -> {
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture1).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture2).visibility =
                                            View.VISIBLE
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[0].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture1))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[1].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture2))
                                    }
                                    3 -> {
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture1).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture2).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture3).visibility =
                                            View.VISIBLE
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[0].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture1))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[1].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture2))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[2].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture3))
                                    }
                                    4 -> {
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture1).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture2).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture3).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture4).visibility =
                                            View.VISIBLE
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[0].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture1))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[1].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture2))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[2].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture3))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[3].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture4))
                                    }
                                    5 -> {
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture1).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture2).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture3).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture4).visibility =
                                            View.VISIBLE
                                        inflate.findViewById<ImageView>(R.id.infoOfferPicture5).visibility =
                                            View.VISIBLE
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[0].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture1))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[1].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture2))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[2].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture3))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[3].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture4))
                                        Glide.with(requireContext())
                                            .load(it.data.productImg[4].imageData)
                                            .fitCenter().error(R.drawable.ic_picture_offer)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                            .into(inflate.findViewById(R.id.infoOfferPicture5))
                                    }
                                }
                            }
                            name = it.data?.productName
                            inflate.findViewById<TextView>(R.id.offerName).text =
                                it.data?.productName
                            val sexField = inflate.findViewById<TextView>(R.id.offerSex)
                            if (it.data?.productSubject == "Cosmétique" || it.data?.productSubject == "Mode") {
                                val sex = "Sexe : ${it.data.productSex}"
                                sexField.text = sex
                                sexField.visibility = View.VISIBLE
                            } else {
                                sexField.visibility = View.GONE
                            }
                            inflate.findViewById<TextView>(R.id.offerDescrption).text =
                                it.data?.productDesc
                            inflate.findViewById<TextView>(R.id.offerSubject).text =
                                it.data?.productSubject
                            getOfferState = true
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
    }

    /**
     * Supprimer une offre
     */
    private fun deleteOffer(token: String?, offerId: Int) {
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.deleteOffer(token = token!!, id = offerId)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Delete Offer", it.message!!)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Delete Offer", it.message!!)
                        }
                    }
                }
            })
    }

    /**
     * Postuler à une offre
     */
    private fun applyOffer(token: String?, offerId: Int) {
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.applyOffer(token = token!!, id = offerId)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                            Log.i("Apply Offer", it.message)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Apply Offer", it.message!!)
                        }
                    }
                }
            })
    }

    /**
     * Annuler la candidature à une offre
     */
    private fun cancelApplyOffer(token: String?, offerId: Int) {
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.cancelApplyOffer(token = token!!, id = offerId)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Cancel Apply Offer", it.message.toString())
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Cancel Apply Offer", it.message.toString())
                        }
                    }
                }
            })
    }

    /**
     * Partager une offre avec un autre utilisateur
     */
    private fun shareOffer() {
        val mDialogView =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_chat, null)
        val mAlertDialogBuilder =
            AlertDialog.Builder(requireContext()).setView(mDialogView)
        val mAlertDialog = mAlertDialogBuilder.show()
        val input = mDialogView.findViewById<TextInputEditText>(R.id.insertMessageOffer)
        mDialogView.findViewById<TextView>(R.id.byMailButton).visibility = View.GONE
        mDialogView.findViewById<TextView>(R.id.byPrivateMessageButton).visibility = View.GONE
        input.visibility = View.VISIBLE
        input.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    shareOfferByMail(email = input.text.toString(), mAlertDialog = mAlertDialog)
                }
                else -> false
            }
        }
    }

    /**
     * Partager par mail
     */
    private fun shareOfferByMail(email: String, mAlertDialog: AlertDialog): Boolean {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val offerId = arguments?.get("idOffer") as Int
        val emailForm = ResetPasswordFirstStepModel()
        emailForm.email = email
        viewModelOffer = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModelOffer.shareOffer(token = token!!, id = offerId, email = emailForm)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(
                                context, it.message.toString(), Toast.LENGTH_LONG
                            ).show()
                            mAlertDialog.dismiss()
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                context, it.message.toString(), Toast.LENGTH_LONG
                            ).show()
                            Log.e("Share Offer", it.message.toString())
                        }
                    }
                }
            })
        return true
    }

    /**
     * Option pour l'offre (Partager, Signaler, Modifier, Candidatures, Supprimer)
     */
    private fun settingsOption(view: View) {
        val offerId = arguments?.get("idOffer") as Int
        val popupMenu = PopupMenu(context, view.findViewById(R.id.settingsButton))
        val userType = DataGetter.INSTANCE.getUserType(requireContext())
        if (userType == "shop") {
            popupMenu.menuInflater.inflate(R.menu.offer_shop_menu, popupMenu.menu)
        } else if (userType == "influencer") {
            popupMenu.menuInflater.inflate(R.menu.offer_inf_menu, popupMenu.menu)
        }
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.markUser -> {
                    findNavController().navigate(R.id.navigation_mark_user)
                }
                R.id.reportUser -> {
                    val bundleReport =
                        bundleOf("type" to "offre", "name" to name, "offerId" to offerId)
                    findNavController().navigate(R.id.navigation_report, bundleReport)
                }
                R.id.editOffer -> {
                    val bundle = bundleOf("offerId" to offerId)
                    findNavController().navigate(R.id.navigation_edit_offer, bundle)
                }
                R.id.removeOffer -> {
                    deleteOffer(
                        token = DataGetter.INSTANCE.getToken(requireContext()),
                        offerId = offerId
                    )
                }
                R.id.appliedUser -> {
                    findNavController().navigate(
                        R.id.navigation_offer_applied,
                        bundleOf("offerId" to offerId)
                    )
                }
                R.id.shareOfferButton -> {
                    shareOffer()
                }
            }
            true
        }
        popupMenu.show()
    }

    companion object {
        var offreReponse: OffreResponseModel? = OffreResponseModel()
    }
}
