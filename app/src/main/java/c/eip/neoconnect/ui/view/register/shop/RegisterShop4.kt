package c.eip.neoconnect.ui.view.register.shop


import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.ui.viewModel.RegisterViewModel
import c.eip.neoconnect.utils.Encoder
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class RegisterShop4 : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private val encoder = Encoder()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_shop_4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_register_shop_3, arguments)
        }
        view.findViewById<Button>(R.id.endRegisterButton).setOnClickListener {
            val shop = RegisterShopModel()
            if (arguments?.get("userPicture") != null) {
                shop.userPicture = arguments?.get("userPicture") as String
            } else {
                shop.userPicture = encoder.encodeTobase64(
                    BitmapFactory.decodeResource(
                        context?.resources,
                        R.drawable.ic_picture_shop
                    )
                )
            }
            if (arguments?.get("pseudo") != null) {
                shop.pseudo = arguments?.get("pseudo") as String
            }
            if (arguments?.get("email") != null) {
                shop.email = arguments?.get("email") as String
            }
            if (arguments?.get("password") != null) {
                shop.password = arguments?.get("password") as String
            }
            if (arguments?.get("name") != null) {
                shop.fullName = arguments?.get("name") as String
            }
            if (arguments?.get("ville") != null) {
                shop.city = arguments?.get("ville") as String
            }
            if (arguments?.get("postal") != null) {
                shop.postal = arguments?.get("postal") as String
            }
            if (arguments?.get("phone") != null) {
                shop.phone = arguments?.get("phone") as String
            }
            if (arguments?.get("society") != null) {
                shop.society = arguments?.get("society") as String
            }
            if (arguments?.get("fonction") != null) {
                shop.fonction = arguments?.get("fonction") as String
            }
            if (arguments?.get("website") != null) {
                shop.website = arguments?.get("website") as String
            }
            if (view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.facebook =
                    view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.twitter =
                    view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.instagram =
                    view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.snapchat =
                    view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerSubject).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerSubject).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.theme =
                    view.findViewById<TextInputEditText>(R.id.registerSubject).text.toString()
            }
            viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
            viewModel.registerShop(shop).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Inscription", "Boutique ${shop.pseudo} inscrit")
                            findNavController().navigate(R.id.navigation_login_shop)
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Inscription", "Echec inscription boutique ${it.message}")
                        }
                    }
                }
            })
        }
    }
}
