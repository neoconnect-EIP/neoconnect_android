package c.eip.neoconnect.ui.view.register.influencer


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.utils.Encoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class RegisterInf2 : Fragment() {
    private val encoder = Encoder()

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_inf_2, container, false)
        if (arguments?.get("userDescription") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerDescription)
                ?.setText(arguments?.get("userDescription") as String)
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
            findNavController().navigate(R.id.navigation_register_inf, arguments)
        }
        view.findViewById<ImageView>(R.id.registerProfilPicture).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<Button>(R.id.nextPageRegister).setOnClickListener {
            val bundle = bundleOf()
            bundle.putAll(arguments)
            val description = view.findViewById<TextInputEditText>(R.id.registerDescription)
            when (description.text.toString().trim().isNotBlank() &&
                    description.text.toString().trim().isNotEmpty() &&
                    description.text.toString().length > 3) {
                true -> bundle.putString("userDescription", description.text.toString())
                false -> bundle.putString("userDescription", null)
            }
            findNavController().navigate(R.id.navigation_register_inf_3, bundle)
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
                .into(requireView().findViewById(R.id.registerProfilPicture))
            view?.findViewById<ImageView>(R.id.registerProfilPicture)?.background = null
            val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImage)
            registerProfilPictureInf = encoder.encodeTobase64(bitmap)
        }
    }

    companion object {
        var registerProfilPictureInf: String = ""
    }
}
