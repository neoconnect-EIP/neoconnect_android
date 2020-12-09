package c.eip.neoconnect.ui.view.register.shop


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.register.CheckFieldModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.ui.viewModel.RegisterViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class RegisterShop4 : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var userViewModel: UserViewModel
    private var themeState: Int = 0
    private var facebookCheck = true
    private var twitterCheck = true
    private var instagramCheck = true
    private var snapchatCheck = true

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Initialisation d'une liste déroulante
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_shop_4, container, false)
        val themeList = resources.getStringArray(R.array.themeSpinner)
        val themeSpinner = inflate.findViewById<Spinner>(R.id.themeSpinner)
        if (themeSpinner != null) {
            val themeAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, themeList)
            themeSpinner.adapter = themeAdapter
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

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     * Inscription Marque
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_register_shop_3, arguments)
        }
        val facebook = view.findViewById<TextInputEditText>(R.id.registerFacebook)
        val twitter = view.findViewById<TextInputEditText>(R.id.registerTwitter)
        val instagram = view.findViewById<TextInputEditText>(R.id.registerInstagram)
        val snapchat = view.findViewById<TextInputEditText>(R.id.registerSnapchat)
        facebook.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.facebook = facebook.text.toString()
                userViewModel = ViewModelProvider(this@RegisterShop4).get(UserViewModel::class.java)
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
                userViewModel = ViewModelProvider(this@RegisterShop4).get(UserViewModel::class.java)
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
                userViewModel = ViewModelProvider(this@RegisterShop4).get(UserViewModel::class.java)
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
                userViewModel = ViewModelProvider(this@RegisterShop4).get(UserViewModel::class.java)
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
        view.findViewById<Button>(R.id.endRegisterButton).setOnClickListener {
            val shop = RegisterShopModel()
            shop.userPicture = RegisterShop2.registerProfilPictureShop
            if (arguments?.get("pseudo") != null) {
                shop.pseudo = arguments?.get("pseudo") as String
            }
            if (arguments?.get("userDescription") != null) {
                shop.userDescription = arguments?.get("userDescription") as String
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
            if (view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString()
                    .trim().isNotEmpty()
            ) {
                shop.website =
                    view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString()
            }
            if (facebook.text.toString().trim().isNotBlank() &&
                facebook.text.toString().trim().isNotEmpty()
            ) {
                shop.facebook = facebook.text.toString()
            } else {
                facebookCheck = true
            }
            if (twitter.text.toString().trim().isNotBlank() &&
                twitter.text.toString().trim().isNotEmpty()
            ) {
                shop.twitter = twitter.text.toString()
            } else {
                twitterCheck = true
            }
            if (instagram.text.toString().trim().isNotBlank() &&
                instagram.text.toString().trim().isNotEmpty()
            ) {
                shop.instagram = instagram.text.toString()
            } else {
                instagramCheck = true
            }
            if (snapchat.text.toString().trim().isNotBlank() &&
                snapchat.text.toString().trim().isNotEmpty()
            ) {
                shop.snapchat = snapchat.text.toString()
            } else {
                snapchatCheck = true
            }
            var checkNetwork = true
            if (!facebookCheck || !twitterCheck || !instagramCheck || !snapchatCheck) {
                Toast.makeText(context,
                    "Veuillez modifier les réseaux sociaux contenant des erreurs",
                    Toast.LENGTH_LONG).show()
                checkNetwork = false
            }
            if (checkNetwork && themeState != 0) {
                shop.theme = themeState.toString()
                viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
                viewModel.registerShop(registerShopModel = shop)
                    .observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    Log.i("Inscription", "Marque ${shop.pseudo} inscrit")
                                    findNavController().navigate(R.id.navigation_login_shop)
                                }
                                Status.ERROR -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    Log.e("Inscription", "Echec inscription marque ${it.message}")
                                    Log.e("Inscription", it.message.toString())
                                }
                            }
                        }
                    })
            } else {
                Toast.makeText(context, "Un thème doit être choisi", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}
