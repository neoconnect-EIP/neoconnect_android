package c.eip.neoconnect.ui.view.offer


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
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
import c.eip.neoconnect.data.model.image.ImagePicture
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.ui.view.feed.FeedShop
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
    private var sexOffreState: Int = -1
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
        val offreSexList = resources.getStringArray(R.array.sexSpinner)
        val offreSexSpinner = inflate.findViewById<Spinner>(R.id.editSexOffreSpinner)
        if (offreThemeSpinner != null) {
            val themeAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, offreThemeList)
            offreThemeSpinner.adapter = themeAdapter
        }
        if (offreSexSpinner != null) {
            val sexAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, offreSexList)
            offreSexSpinner.adapter = sexAdapter
        }
        offreThemeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                themeOffreState = position
                if (themeOffreState == 1 || themeOffreState == 2) {
                    inflate.findViewById<Spinner>(R.id.editSexOffreSpinner).visibility = View.VISIBLE
                } else {
                    inflate.findViewById<Spinner>(R.id.editSexOffreSpinner).visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        offreSexSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sexOffreState = position
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
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<ImageView>(R.id.editOfferPicture1).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }

        val nameOfferInput = view.findViewById<TextInputEditText>(R.id.editOfferName)
        var checkProductName = false
        nameOfferInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (nameOfferInput.text.toString().trim().isBlank() ||
                    nameOfferInput.text.toString().trim().isEmpty()
                ) {
                    nameOfferInput.error = "Le nom du produit est vide"
                } else if (nameOfferInput.text.toString().trim().isNotBlank() &&
                    nameOfferInput.text.toString().trim().isNotEmpty() &&
                    nameOfferInput.text.toString().length < 3
                ) {
                    nameOfferInput.error = "Le nom du produit contient moins de 3 caractères"
                } else {
                    checkProductName = true
                }
            }
        })
        val descOfferInput = view.findViewById<TextInputEditText>(R.id.editOfferDescription)
        var checkProductDesc = false
        descOfferInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (descOfferInput.text.toString().trim().isBlank() ||
                    descOfferInput.text.toString().trim().isEmpty()
                ) {
                    descOfferInput.error = "La description du produit est vide"
                } else if (descOfferInput.text.toString().trim().isNotBlank() &&
                    descOfferInput.text.toString().trim().isNotEmpty() &&
                    descOfferInput.text.toString().length < 3
                ) {
                    descOfferInput.error =
                        "La description du produit contient moins de 3 caractères"
                } else {
                    checkProductDesc = true
                }
            }
        })
        val themeOfferInput = themeOffreState.toString()
        var checkProductTheme = false
        if (themeOfferInput.isNotBlank() || themeOfferInput.isNotEmpty() || themeOffreState > 0) {
            if (themeOffreState == 1 || themeOffreState == 2) {
                if (sexOffreState > -1) {
                    checkProductTheme = true
                }
            } else {
                checkProductTheme = true
            }
        }
        view.findViewById<Button>(R.id.editOfferButton).setOnClickListener {
            if (checkProductName && checkProductDesc && checkProductTheme && editOfferPicture.size > 0) {
                val offer = OffreModel()
                val token = DataGetter.INSTANCE.getToken(requireContext())
                val offreSexList = resources.getStringArray(R.array.sexSpinner)
                val sexOfferInput = offreSexList[sexOffreState]
                offer.productImg = editOfferPicture
                offer.productName = nameOfferInput.text.toString()
                offer.productDesc = descOfferInput.text.toString()
                offer.productSubject = themeOfferInput
                offer.brand = FeedShop.shopData?.pseudo
                if (themeOffreState == 1 || themeOffreState == 2) {
                    offer.productSex = sexOfferInput
                }
                editOffer(token = token!!, offre = offer)
            } else if (!checkProductTheme) {
                Toast.makeText(context, "Un thème doit être sélectionné", Toast.LENGTH_SHORT).show()
            } else if ((themeOffreState == 1 || themeOffreState == 2) && sexOffreState < 0) {
                Toast.makeText(context, "Un sexe doit être sélectionné", Toast.LENGTH_SHORT).show()
            }
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
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
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
                        inflate.findViewById<TextInputEditText>(R.id.editOfferDescription)
                            .setText(it.data?.productDesc)
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
    private fun editOffer(token: String, offre: OffreModel) {
        val offerId = arguments?.get("offerId") as Int
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModel.editOffer(token = token, id = offerId, offre = offre)
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
