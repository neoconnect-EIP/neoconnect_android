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
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.ImagePicture
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.ui.view.feed.FeedShop
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Encoder
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class InsertOffer : Fragment() {
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
        val inflate = inflater.inflate(R.layout.fragment_insert_offer, container, false)
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        val offreThemeSpinner = inflate.findViewById<Spinner>(R.id.themeOffreSpinner)
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
        val nameOfferInput = view.findViewById<TextInputEditText>(R.id.insertOfferName)
        val descOfferInput = view.findViewById<TextInputEditText>(R.id.insertOfferDescription)
        val colorOfferInput = view.findViewById<TextInputEditText>(R.id.insertOfferColor)
        view.findViewById<TextView>(R.id.resetForm).setOnClickListener {
            view.findViewById<ImageView>(R.id.insertOfferPicture1).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture2).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture3).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture4).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture5).setImageURI(null)
            nameOfferInput.text = null
            descOfferInput.text = null
            view.findViewById<RadioButton>(R.id.insertOfferSexFemme).isChecked = false
            view.findViewById<RadioButton>(R.id.insertOfferSexHomme).isChecked = false
            view.findViewById<Spinner>(R.id.themeOffreSpinner).setSelection(0)
            colorOfferInput.text = null
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture1).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<Button>(R.id.insertOfferButton).setOnClickListener {
            insertOffer(
                view = view,
                nameOfferInput = nameOfferInput,
                colorOfferInput = colorOfferInput,
                descOfferInput = descOfferInput
            )
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
            insertOfferPicture.add(picture)
        }
    }

    /**
     * Poster une nouvelle offre
     */
    private fun insertOffer(
        view: View,
        nameOfferInput: TextView,
        descOfferInput: TextView,
        colorOfferInput: TextView
    ) {
        val productPicture = insertOfferPicture
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        val productName = nameOfferInput.text.toString()
        val productDesc = descOfferInput.text.toString()
        val productSex =
            when (view.findViewById<RadioGroup>(R.id.insertOfferSex).checkedRadioButtonId) {
                R.id.insertOfferSexFemme -> "Femme"
                R.id.insertOfferSexHomme -> "Homme"
                else -> null
            }
        val productSubject = offreThemeList[themeOffreState]
        val productColor = colorOfferInput.text.toString()
        if (productName.isNotEmpty() && productName.isNotBlank() && productDesc.isNotEmpty() && productDesc.isNotBlank() && productSubject.isNotBlank() && productSubject.isNotEmpty() && productColor.isNotEmpty() && productColor.isNotBlank() && !productSex.isNullOrBlank()) {
            val offer = OffreModel()
            val token = DataGetter.INSTANCE.getToken(requireContext())
            offer.productImg = productPicture
            offer.productName = productName
            offer.productSex = productSex
            offer.productDesc = productDesc
            offer.productSubject = productSubject
            offer.brand = FeedShop.shopData?.pseudo
            offer.color = productColor
            viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
            viewModel.insertOffer(token = token!!, offre = offer)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                Log.i(
                                    "Offre",
                                    "${DataGetter.INSTANCE.getUserId(requireContext())} a ajouté l'offre ${it.data?.id}"
                                )
                                view.findViewById<ImageView>(R.id.insertOfferPicture1)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture2)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture3)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture4)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture5)
                                    .setImageURI(null)
                                nameOfferInput.text = null
                                descOfferInput.text = null
                                view.findViewById<RadioButton>(R.id.insertOfferSexFemme).isChecked =
                                    false
                                view.findViewById<RadioButton>(R.id.insertOfferSexHomme).isChecked =
                                    false
                                view.findViewById<Spinner>(R.id.themeOffreSpinner)
                                    .setSelection(0)
                                colorOfferInput.text = null
                            }
                            Status.ERROR -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.e("Offre", it.message!!)
                            }
                        }
                    }
                }
                )
        }
    }

    companion object {
        var insertOfferPicture: ArrayList<ImagePicture> = ArrayList()
    }
}
