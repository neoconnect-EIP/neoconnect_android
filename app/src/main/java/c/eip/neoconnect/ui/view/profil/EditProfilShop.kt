package c.eip.neoconnect.ui.view.profil


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
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Encoder
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class EditProfilShop : Fragment() {
    private lateinit var viewModel: ShopViewModel
    private var profilData = ProfilShop.shopData
    private var themeState: Int = 0
    private val encoder = Encoder()

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
        inflate.findViewById<TextInputEditText>(R.id.editProfilPseudo).setText(profilData?.pseudo)
        inflate.findViewById<TextInputEditText>(R.id.editProfilEmail).setText(profilData?.email)
        inflate.findViewById<TextInputEditText>(R.id.editProfilNom).setText(profilData?.fullName)
        inflate.findViewById<TextInputEditText>(R.id.editProfilPhone).setText(profilData?.phone)
        inflate.findViewById<TextInputEditText>(R.id.editProfilVille).setText(profilData?.city)
        inflate.findViewById<TextInputEditText>(R.id.editProfilPostal).setText(profilData?.postal)
        inflate.findViewById<TextInputEditText>(R.id.editProfilSociety).setText(profilData?.society)
        inflate.findViewById<TextInputEditText>(R.id.editProfilFunction)
            .setText(profilData?.fonction)
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
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.editMyProfilPicture).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }

        view.findViewById<TextView>(R.id.saveButton).setOnClickListener {
            val token = DataGetter.INSTANCE.getToken(requireContext())
            val shop = RegisterShopModel()
            shop.userPicture = editProfilPictureShop
            shop.pseudo =
                view.findViewById<TextInputEditText>(R.id.editProfilPseudo).text.toString()
            shop.email = view.findViewById<TextInputEditText>(R.id.editProfilEmail).text.toString()
            shop.fullName = view.findViewById<TextInputEditText>(R.id.editProfilNom).text.toString()
            shop.phone = view.findViewById<TextInputEditText>(R.id.editProfilPhone).text.toString()
            shop.city = view.findViewById<TextInputEditText>(R.id.editProfilVille).text.toString()
            shop.postal =
                view.findViewById<TextInputEditText>(R.id.editProfilPostal).text.toString()
            shop.society =
                view.findViewById<TextInputEditText>(R.id.editProfilSociety).text.toString()
            shop.fonction =
                view.findViewById<TextInputEditText>(R.id.editProfilFunction).text.toString()
            shop.website =
                view.findViewById<TextInputEditText>(R.id.editProfilWebsite).text.toString()
            shop.facebook =
                view.findViewById<TextInputEditText>(R.id.editProfilFacebook).text.toString()
            shop.twitter =
                view.findViewById<TextInputEditText>(R.id.editProfilTwitter).text.toString()
            shop.instagram =
                view.findViewById<TextInputEditText>(R.id.editProfilInstagram).text.toString()
            shop.snapchat =
                view.findViewById<TextInputEditText>(R.id.editProfilSnapchat).text.toString()
            if (themeState != 0) {
                val themeList = resources.getStringArray(R.array.themeSpinner)
                shop.theme = themeList[themeState]
            }
            viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
            viewModel.updateProfilShop(token!!, shop).observe(viewLifecycleOwner, Observer {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Edit Shop", "Shop ${shop.pseudo} mis à jour")
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Edit Shop", "Echec mise à jour shop ${it.message}")
                        }
                        else -> {
                        }
                    }
                }
            })
        }
    }

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

    companion object {
        var editProfilPictureShop: String = ""
    }
}
