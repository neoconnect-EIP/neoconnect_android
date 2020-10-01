package c.eip.neoconnect.ui.view.register.shop

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
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.Encoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class RegisterShop1 : Fragment() {
    private val checkInput = CheckInput()
    private val encoder = Encoder()
    val bundle = bundleOf()

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_shop_1, container, false)
        if (!requireArguments().isEmpty) {
            inflate.findViewById<TextInputEditText>(R.id.registerPseudo)
                ?.setText(arguments?.get("pseudo") as String)
            inflate.findViewById<TextInputEditText>(R.id.registerEmail)
                ?.setText(arguments?.get("email") as String)
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
            findNavController().navigate(R.id.navigation_login_shop)
        }
        view.findViewById<ImageView>(R.id.registerProfilPicture).setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
            openGallery()
        }
        view.findViewById<Button>(R.id.nextPageRegister).setOnClickListener {
            val pseudo = view.findViewById<TextInputEditText>(R.id.registerPseudo)?.text.toString()
            val email = view.findViewById<TextInputEditText>(R.id.registerEmail)?.text.toString()
            val password =
                view.findViewById<TextInputEditText>(R.id.registerPassword)?.text.toString()
            val passwordCheck =
                view.findViewById<TextInputEditText>(R.id.registerPasswordConfirm)?.text.toString()
            val checkPseudo = checkInput.checkPseudo(pseudo)
            if (!checkPseudo) {
                Toast.makeText(
                    context,
                    "Le pseudo ne doit pas être vide et doit contenir entre 4 et 12 caractères",
                    Toast.LENGTH_LONG
                ).show()
            }
            val checkEmail = checkInput.checkEmail(email)
            if (!checkEmail) {
                Toast.makeText(
                    context,
                    "L'adresse mail doit être valide",
                    Toast.LENGTH_LONG
                ).show()
            }
            val checkPassword = checkInput.checkPassword(password, passwordCheck)
            when (checkPassword) {
                1 -> Toast.makeText(
                    context,
                    "Le mot de passe ne doit pas être vide",
                    Toast.LENGTH_LONG
                ).show()
                2 -> Toast.makeText(
                    context,
                    "Le mot de passe de confirmation ne doit pas être vide",
                    Toast.LENGTH_LONG
                ).show()
                3 -> Toast.makeText(
                    context,
                    "Le mot de passe et le mot de passe de confirmation ne doivent pas être différents",
                    Toast.LENGTH_LONG
                ).show()
                4 -> Toast.makeText(
                    context,
                    "Le mot de passe doit contenir entre 4 et 12 caractères, avec 1 minuscule, 1 majuscule et 1 chiffre",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (checkPseudo && checkEmail && checkPassword == 0) {
                bundle.putString("pseudo", pseudo)
                bundle.putString("password", password)
                bundle.putString("email", email)
                findNavController().navigate(R.id.navigation_register_shop_2, bundle)
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
                .into(requireView().findViewById(R.id.registerProfilPicture))
            view?.findViewById<ImageView>(R.id.registerProfilPicture)?.background = null
            val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImage)
            registerProfilPictureShop = encoder.encodeTobase64(bitmap)
        }
    }

    companion object {
        var registerProfilPictureShop: String = ""
    }
}
