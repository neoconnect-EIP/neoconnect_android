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
import java.time.LocalDateTime

class InsertOffer : Fragment() {
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
        val inflate = inflater.inflate(R.layout.fragment_insert_offer, container, false)
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        val offreThemeSpinner = inflate.findViewById<Spinner>(R.id.themeOffreSpinner)
        val offreSexList = resources.getStringArray(R.array.sexSpinner)
        val offreSexSpinner = inflate.findViewById<Spinner>(R.id.sexOffreSpinner)
        val theme = DataGetter.INSTANCE.getTheme(requireContext())
        if (offreThemeSpinner != null) {
            val themeAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, offreThemeList)
            offreThemeSpinner.adapter = themeAdapter
            offreThemeSpinner.setSelection(offreThemeList.indexOf(theme))
            themeOffreState = offreThemeList.indexOf(theme)
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
                    inflate.findViewById<Spinner>(R.id.sexOffreSpinner).visibility = View.VISIBLE
                } else {
                    inflate.findViewById<Spinner>(R.id.sexOffreSpinner).visibility = View.GONE
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
        val nameOfferInput = view.findViewById<TextInputEditText>(R.id.insertOfferName)
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
        val descOfferInput = view.findViewById<TextInputEditText>(R.id.insertOfferDescription)
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
        view.findViewById<TextView>(R.id.resetForm).setOnClickListener {
            view.findViewById<ImageView>(R.id.insertOfferPicture).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture1).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture2).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture3).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture4).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture5).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.insertOfferPicture1).visibility = View.GONE
            view.findViewById<ImageView>(R.id.insertOfferPicture2).visibility = View.GONE
            view.findViewById<ImageView>(R.id.insertOfferPicture3).visibility = View.GONE
            view.findViewById<ImageView>(R.id.insertOfferPicture4).visibility = View.GONE
            view.findViewById<ImageView>(R.id.insertOfferPicture5).visibility = View.GONE
            nameOfferInput.text = null
            nameOfferInput.error = null
            descOfferInput.text = null
            descOfferInput.error = null
            view.findViewById<Spinner>(R.id.themeOffreSpinner).setSelection(0)
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture1).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture2).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture3).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture4).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<ImageView>(R.id.insertOfferPicture5).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<Button>(R.id.insertOfferButton).setOnClickListener {
            if (checkProductName && checkProductDesc && checkProductTheme && insertOfferPicture.size > 0) {
                val offer = OffreModel()
                val token = DataGetter.INSTANCE.getToken(requireContext())
                val offreSexList = resources.getStringArray(R.array.sexSpinner)
                offer.productImg = insertOfferPicture
                offer.productName = nameOfferInput.text.toString()
                offer.productDesc = descOfferInput.text.toString()
                offer.productSubject = themeOffreState.toString()
                offer.brand = FeedShop.shopData?.pseudo
                if (themeOffreState == 1 || themeOffreState == 2) {
                    val sexOfferInput = offreSexList[sexOffreState]
                    offer.productSex = sexOfferInput
                }
                insertOffer(token = token!!, offre = offer)
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
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
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
            if (data.clipData != null) {
                val count = data.clipData!!.itemCount
                insertOfferPicture.clear()
                for (i in 0 until count) {
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap( context?.contentResolver, data.clipData!!.getItemAt(i).uri)
                    val resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true)
                    val imagePicture = ImagePicture()
                    imagePicture.imageData = encoder.encodeTobase64(resized)
                    val userId = DataGetter.INSTANCE.getUserId(requireContext())
                    imagePicture.imageName = userId.toString() + "_" + i.toString() + LocalDateTime.now()
                    insertOfferPicture.add(imagePicture)
                }
                val picture = view?.findViewById<ImageView>(R.id.insertOfferPicture)
                val picture1 = view?.findViewById<ImageView>(R.id.insertOfferPicture1)
                val picture2 = view?.findViewById<ImageView>(R.id.insertOfferPicture2)
                val picture3 = view?.findViewById<ImageView>(R.id.insertOfferPicture3)
                val picture4 = view?.findViewById<ImageView>(R.id.insertOfferPicture4)
                val picture5 = view?.findViewById<ImageView>(R.id.insertOfferPicture5)
                when (count) {
                    1 -> {
                        view?.findViewById<HorizontalScrollView>(R.id.hScrollViewOfferInsert)?.visibility =
                            View.GONE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(0).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture))
                        picture?.visibility = View.VISIBLE
                        picture?.background = null
                        picture1?.visibility = View.GONE
                        picture2?.visibility = View.GONE
                        picture3?.visibility = View.GONE
                        picture4?.visibility = View.GONE
                        picture5?.visibility = View.GONE
                    }
                    2 -> {
                        picture?.visibility = View.GONE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(0).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture1))
                        picture1?.background = null
                        picture1?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(1).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture2))
                        picture2?.background = null
                        picture2?.visibility = View.VISIBLE
                        picture3?.visibility = View.GONE
                        picture4?.visibility = View.GONE
                        picture5?.visibility = View.GONE
                    }
                    3 -> {
                        picture?.visibility = View.GONE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(0).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture1))
                        picture1?.background = null
                        picture1?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(1).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture2))
                        picture2?.background = null
                        picture2?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(2).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture3))
                        picture3?.background = null
                        picture3?.visibility = View.VISIBLE
                        picture4?.visibility = View.GONE
                        picture5?.visibility = View.GONE
                    }
                    4 -> {
                        picture?.visibility = View.GONE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(0).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture1))
                        picture1?.background = null
                        picture1?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(1).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture2))
                        picture2?.background = null
                        picture2?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(2).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture3))
                        picture3?.background = null
                        picture3?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(3).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture4))
                        picture4?.background = null
                        picture4?.visibility = View.VISIBLE
                        picture5?.visibility = View.GONE
                    }
                    5 -> {
                        picture?.visibility = View.GONE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(0).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture1))
                        picture1?.background = null
                        picture1?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(1).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture2))
                        picture2?.background = null
                        picture2?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(2).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture3))
                        picture3?.background = null
                        picture3?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(3).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture4))
                        picture4?.background = null
                        picture4?.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(data.clipData!!.getItemAt(4).uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(requireView().findViewById(R.id.insertOfferPicture5))
                        picture5?.background = null
                        picture5?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * Poster une nouvelle offre
     */
    private fun insertOffer(token: String, offre: OffreModel) {
        viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        viewModel.insertOffer(token = token, offre = offre)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i(
                                "Offre",
                                "${DataGetter.INSTANCE.getUserId(requireContext())} a ajouté l'offre ${it.data?.id}"
                            )
                            findNavController().navigate(R.id.navigation_insert_offer)
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

    companion object {
        var insertOfferPicture = ArrayList<ImagePicture>()
    }
}
