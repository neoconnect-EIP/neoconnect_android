package c.eip.neoconnect.ui.view.offer


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.ImagePicture
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Encoder
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class EditOffer : Fragment() {
    private lateinit var viewModel: OffresViewModel
    private var themeOffreState: Int = 0
    private val encoder = Encoder()

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Initialisation de la liste déroulante
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_edit_offer, container, false)
        getOneOffer(inflate = inflate)
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        val offreThemeSpinner = inflate.findViewById<Spinner>(R.id.editThemeOffreSpinner)
        if (offreThemeSpinner != null) {
            val themeAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, offreThemeList)
            offreThemeSpinner.adapter = themeAdapter
        }
        offreThemeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                themeOffreState = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
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
        view.findViewById<ImageView>(R.id.editOfferPicture1).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<Button>(R.id.editOfferButton).setOnClickListener {
            editOffer(view = view)
        }
    }

    /**
     * Vérifier permissions
     * Ouvrir storage
     */
    private fun openGallery() {
        if (context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        } else {
            val toast = Toast.makeText(context, "Autorisation non accordé", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }
    }

    /**
     * Sélection d'une image
     * Affichage de l'image sélectionné
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val selectedImage = data.data
            Glide.with(requireContext()).load(selectedImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(requireView().findViewById(R.id.insertOfferPicture1))
            view?.findViewById<ImageView>(R.id.insertOfferPicture1)?.background = null
            val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImage)
            val picture = ImagePicture()
            picture.imageData = encoder.encodeTobase64(bitmap)
            editOfferPicture.add(picture)
        }
    }

    /**
     * Récupération des informations de l'offre
     */
    private fun getOneOffer(inflate: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val offerId = arguments?.get("offerId") as Int
        viewModel.getOneOffer(token = token!!, id = offerId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.productImg.isNullOrEmpty()) {
                            inflate.findViewById<ImageView>(R.id.editOfferPicture1)
                                .setImageResource(R.drawable.ic_picture_offer)
                            inflate.findViewById<ImageView>(R.id.editOfferPicture2).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.editOfferPicture3).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.editOfferPicture4).visibility =
                                View.GONE
                            inflate.findViewById<ImageView>(R.id.editOfferPicture5).visibility =
                                View.GONE
                        } else {
                            Glide.with(requireContext()).load(it.data!!.productImg[0].imageData)
                                .fitCenter().error(R.drawable.ic_picture_offer)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(inflate.findViewById(R.id.editOfferPicture1))
                        }
                        inflate.findViewById<TextInputEditText>(R.id.editOfferName)
                            .setText(it.data?.productName)
                        when (it.data?.productSex) {
                            "Homme" -> {
                                inflate.findViewById<RadioButton>(R.id.editOfferSexHomme).isChecked =
                                    true
                                inflate.findViewById<RadioButton>(R.id.editOfferSexFemme).isChecked =
                                    false
                            }
                            "Femme" -> {
                                inflate.findViewById<RadioButton>(R.id.editOfferSexHomme).isChecked =
                                    false
                                inflate.findViewById<RadioButton>(R.id.editOfferSexFemme).isChecked =
                                    true
                            }
                            else -> {
                                inflate.findViewById<RadioButton>(R.id.editOfferSexHomme).isChecked =
                                    false
                                inflate.findViewById<RadioButton>(R.id.editOfferSexFemme).isChecked =
                                    false
                            }
                        }
                        inflate.findViewById<TextInputEditText>(R.id.editOfferDescription)
                            .setText(it.data?.productDesc)
                        inflate.findViewById<TextInputEditText>(R.id.editOfferColor)
                            .setText(it.data?.color)
                        val themeList = resources.getStringArray(R.array.themeSpinner)
                        if (themeList.indexOf(it.data?.productSubject) != -1) {
                            inflate.findViewById<Spinner>(R.id.editThemeOffreSpinner)
                                .setSelection(themeList.indexOf(it.data?.productSubject))
                            themeOffreState = themeList.indexOf(it.data?.productSubject)
                        } else {
                            inflate.findViewById<Spinner>(R.id.editThemeOffreSpinner)
                                .setSelection(0)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Offre", it.message!!)
                    }
                }
            }
        })

    }

    /**
     * Mettre à jour une offre
     */
    private fun editOffer(view: View) {
        val offreModel = OffreModel()
        offreModel.productImg = editOfferPicture
        offreModel.productName =
            view.findViewById<TextInputEditText>(R.id.editOfferName).text.toString()
        offreModel.productDesc =
            view.findViewById<TextInputEditText>(R.id.editOfferDescription).text.toString()
        offreModel.color =
            view.findViewById<TextInputEditText>(R.id.editOfferColor).text.toString()
        offreModel.productSex =
            when (view.findViewById<RadioGroup>(R.id.editOfferSex).checkedRadioButtonId) {
                R.id.editOfferSexFemme -> "Femme"
                R.id.editOfferSexHomme -> "Homme"
                else -> null
            }
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        offreModel.productSubject = offreThemeList[themeOffreState]
        offreModel.brand = MainViewShop.shopData?.pseudo
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val offerId = arguments?.get("offerId") as Int
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModel.editOffer(token = token!!, id = offerId, offre = offreModel)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i(
                                "Edit Offre",
                                "${DataGetter.INSTANCE.getUserId(requireContext())} a modifié l'offre ${it.data?.id}"
                            )
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Edit Offer", it.message!!)
                        }
                    }
                }
            })
    }

    companion object {
        var editOfferPicture: ArrayList<ImagePicture> = ArrayList()
    }
}
