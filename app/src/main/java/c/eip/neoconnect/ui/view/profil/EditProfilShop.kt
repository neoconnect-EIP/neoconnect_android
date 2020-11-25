package c.eip.neoconnect.ui.view.profil


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
import c.eip.neoconnect.data.model.register.CheckFieldModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Encoder
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class EditProfilShop : Fragment() {
    private lateinit var viewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    private var profilData = ProfilShop.shopData
    private var themeState: Int = 0
    private val encoder = Encoder()
    private val checkInput = CheckInput()
    private var emailCheck = false
    private var facebookCheck = true
    private var twitterCheck = true
    private var instagramCheck = true
    private var snapchatCheck = true

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_edit_profil_shop, container, false)
        if (profilData?.userPicture.isNullOrEmpty()) {
            inflate.findViewById<ImageView>(R.id.editMyProfilPicture)
                .setImageResource(R.drawable.ic_picture_shop)
        } else {
            Glide.with(requireContext()).load(profilData?.userPicture?.get(0)?.imageData)
                .circleCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_picture_shop)
                .into(inflate.findViewById(R.id.editMyProfilPicture))
        }
        inflate.findViewById<TextInputEditText>(R.id.editProfilDescription)
            .setText(profilData?.userDescription)
        inflate.findViewById<TextView>(R.id.editProfilPseudo).text = profilData?.pseudo
        inflate.findViewById<TextInputEditText>(R.id.editProfilEmail).setText(profilData?.email)
        inflate.findViewById<TextInputEditText>(R.id.editProfilNom).setText(profilData?.fullName)
        inflate.findViewById<TextInputEditText>(R.id.editProfilPhone).setText(profilData?.phone)
        inflate.findViewById<TextInputEditText>(R.id.editProfilVille).setText(profilData?.city)
        inflate.findViewById<TextInputEditText>(R.id.editProfilPostal).setText(profilData?.postal)
        inflate.findViewById<TextInputEditText>(R.id.editProfilWebsite).setText(profilData?.website)
        inflate.findViewById<TextInputEditText>(R.id.editProfilFacebook)
            .setText(profilData?.facebook)
        inflate.findViewById<TextInputEditText>(R.id.editProfilTwitter).setText(profilData?.twitter)
        inflate.findViewById<TextInputEditText>(R.id.editProfilInstagram)
            .setText(profilData?.instagram)
        inflate.findViewById<TextInputEditText>(R.id.editProfilSnapchat)
            .setText(profilData?.snapchat)
        val themeList = resources.getStringArray(R.array.themeSpinner)
        val themeSpinner = inflate.findViewById<Spinner>(R.id.editProfilSubject)
        if (themeSpinner != null) {
            val themeAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, themeList)
            themeSpinner.adapter = themeAdapter
            if (themeList.indexOf(profilData?.theme) != -1) {
                themeSpinner.setSelection(themeList.indexOf(profilData?.theme))
                themeState = themeList.indexOf(profilData?.theme)
            } else {
                themeSpinner.setSelection(0)
            }
        }
        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                themeState = position
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
        view.findViewById<ImageView>(R.id.editMyProfilPicture).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        val descInput = view.findViewById<TextInputEditText>(R.id.editProfilDescription)
        var checkDesc = true
        descInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (descInput.text.toString().length < 3) {
                    descInput.error = "La description doit contenir plus de 3 caractères"
                    checkDesc = false
                } else {
                    descInput.error = null
                    checkDesc = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        val emailInput = view.findViewById<TextInputEditText>(R.id.editProfilEmail)
        emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val checkEmail = checkInput.checkEmail(emailInput.text.toString())
                if (!checkEmail) {
                    emailInput.error = "L'adresse mail doit être valide"
                    emailCheck = false
                } else {
                    val field = CheckFieldModel()
                    field.email = emailInput.text.toString()
                    userViewModel =
                        ViewModelProvider(this@EditProfilShop).get(UserViewModel::class.java)
                    userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    if (it.data == true) {
                                        if (emailInput.text.toString() == profilData?.email) {
                                            emailCheck = true
                                        } else {
                                            emailInput.error = "Cette adresse mail est déjà prise"
                                            emailCheck = false
                                        }
                                    } else {
                                        emailInput.error = null
                                        emailCheck = true
                                    }
                                }
                                Status.ERROR -> {
                                    Log.e("Check Field", it.message!!)
                                }
                            }
                        }
                    })
                }

            }
        })

        val facebook = view.findViewById<TextInputEditText>(R.id.editProfilFacebook)
        val twitter = view.findViewById<TextInputEditText>(R.id.editProfilTwitter)
        val instagram = view.findViewById<TextInputEditText>(R.id.editProfilInstagram)
        val snapchat = view.findViewById<TextInputEditText>(R.id.editProfilSnapchat)

        facebook.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.facebook = facebook.text.toString()
                userViewModel =
                    ViewModelProvider(this@EditProfilShop).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    facebook.error = "Ce compte facebook est déjà enregistré"
                                    facebookCheck = false
                                } else {
                                    facebook.error = null
                                    facebookCheck = true
                                }
                            }
                            Status.ERROR -> {
                                Log.e("Check Field", it.message!!)
                            }
                        }
                    }
                })
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        twitter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.twitter = twitter.text.toString()
                userViewModel =
                    ViewModelProvider(this@EditProfilShop).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    twitter.error = "Ce compte twitter est déjà enregistré"
                                    twitterCheck = false
                                } else {
                                    twitter.error = null
                                    twitterCheck = true
                                }
                            }
                            Status.ERROR -> {
                                Log.e("Check Field", it.message!!)
                            }
                        }
                    }
                })
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        instagram.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.instagram = instagram.text.toString()
                userViewModel =
                    ViewModelProvider(this@EditProfilShop).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    instagram.error = "Ce compte instagram est déjà enregistré"
                                    instagramCheck = false
                                } else {
                                    instagram.error = null
                                    instagramCheck = true
                                }
                            }
                            Status.ERROR -> {
                                Log.e("Check Field", it.message!!)
                            }
                        }
                    }
                })
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        snapchat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.snapchat = snapchat.text.toString()
                userViewModel =
                    ViewModelProvider(this@EditProfilShop).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    snapchat.error = "Ce compte snapchat est déjà enregistré"
                                    snapchatCheck = false
                                } else {
                                    snapchat.error = null
                                    snapchatCheck = true
                                }
                            }
                            Status.ERROR -> {
                                Log.e("Check Field", it.message!!)
                            }
                        }
                    }
                })
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        view.findViewById<TextView>(R.id.saveButton).setOnClickListener {
            if (checkDesc && facebookCheck && twitterCheck && emailCheck && snapchatCheck && instagramCheck) {
                editProfilShop(view = view)
            } else {
                Toast.makeText(context,
                    "Veuillez modifier les champs contenant des erreurs",
                    Toast.LENGTH_LONG).show()
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
            Glide.with(requireContext()).load(selectedImage).circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(requireView().findViewById(R.id.editMyProfilPicture))
            view?.findViewById<ImageView>(R.id.editMyProfilPicture)?.background = null
            val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImage)
            editProfilPictureShop = encoder.encodeTobase64(bitmap)
        }
    }

    /**
     * Modifier son profil Marque
     */
    private fun editProfilShop(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val shop = RegisterShopModel()
        if (editProfilPictureShop.isNotEmpty()) {
            shop.userPicture = editProfilPictureShop
        }
        if (profilData?.userDescription != view.findViewById<TextInputEditText>(R.id.editProfilDescription).text.toString())
            shop.userDescription =
                view.findViewById<TextInputEditText>(R.id.editProfilDescription).text.toString()
        if (profilData?.email != view.findViewById<TextInputEditText>(R.id.editProfilEmail).text.toString())
            shop.email = view.findViewById<TextInputEditText>(R.id.editProfilEmail).text.toString()
        if (profilData?.phone != view.findViewById<TextInputEditText>(R.id.editProfilPhone).text.toString())
            shop.phone = view.findViewById<TextInputEditText>(R.id.editProfilPhone).text.toString()
        if (profilData?.city != view.findViewById<TextInputEditText>(R.id.editProfilVille).text.toString())
            shop.city = view.findViewById<TextInputEditText>(R.id.editProfilVille).text.toString()
        if (profilData?.postal != view.findViewById<TextInputEditText>(R.id.editProfilPostal).text.toString())
            shop.postal =
                view.findViewById<TextInputEditText>(R.id.editProfilPostal).text.toString()
        if (profilData?.website != view.findViewById<TextInputEditText>(R.id.editProfilWebsite).text.toString())
            shop.website =
                view.findViewById<TextInputEditText>(R.id.editProfilWebsite).text.toString()
        if (profilData?.facebook != view.findViewById<TextInputEditText>(R.id.editProfilFacebook).text.toString())
            shop.facebook =
                view.findViewById<TextInputEditText>(R.id.editProfilFacebook).text.toString()
        if (profilData?.twitter != view.findViewById<TextInputEditText>(R.id.editProfilTwitter).text.toString())
            shop.twitter =
                view.findViewById<TextInputEditText>(R.id.editProfilTwitter).text.toString()
        if (profilData?.instagram != view.findViewById<TextInputEditText>(R.id.editProfilInstagram).text.toString())
            shop.instagram =
                view.findViewById<TextInputEditText>(R.id.editProfilInstagram).text.toString()
        if (profilData?.snapchat != view.findViewById<TextInputEditText>(R.id.editProfilSnapchat).text.toString())
            shop.snapchat =
                view.findViewById<TextInputEditText>(R.id.editProfilSnapchat).text.toString()
        if (themeState != 0) {
            shop.theme = themeState.toString()
        }
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        viewModel.updateProfilShop(token = token!!, shop = shop)
            .observe(viewLifecycleOwner, Observer {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            DataGetter.INSTANCE.saveTheme(requireContext(), themeState.toString())
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Edit Shop", "Shop ${shop.pseudo} mis à jour")
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Edit Shop", "Echec mise à jour shop ${it.message}")
                        }
                    }
                }
            })
    }

    companion object {
        var editProfilPictureShop: String = ""
    }
}
