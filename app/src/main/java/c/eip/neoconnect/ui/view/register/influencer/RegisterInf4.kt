package c.eip.neoconnect.ui.view.register.influencer


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
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.ui.viewModel.RegisterViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class RegisterInf4 : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var userViewModel: UserViewModel
    private var themeState: Int = 0
    private var facebookCheck = true
    private var twitterCheck = true
    private var instagramCheck = true
    private var snapchatCheck = true
    private var youtubeCheck = true
    private var twitchCheck = true
    private var pinterestCheck = true
    private var tiktokCheck = true

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Initialisation d'une liste déroulante
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_inf_4, container, false)
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
     * Inscription Influenceur
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_register_inf_3, arguments)
        }
        val facebook = view.findViewById<TextInputEditText>(R.id.registerFacebook)
        val twitter = view.findViewById<TextInputEditText>(R.id.registerTwitter)
        val instagram = view.findViewById<TextInputEditText>(R.id.registerInstagram)
        val snapchat = view.findViewById<TextInputEditText>(R.id.registerSnapchat)
        val youtube = view.findViewById<TextInputEditText>(R.id.registerYoutube)
        val twitch = view.findViewById<TextInputEditText>(R.id.registerTwitch)
        val pinterest = view.findViewById<TextInputEditText>(R.id.registerPinterest)
        val tiktok = view.findViewById<TextInputEditText>(R.id.registerTiktok)

        facebook.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.facebook = facebook.text.toString()
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    facebookCheck = false
                                } else {
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
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    twitterCheck = false
                                } else {
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
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    instagramCheck = false
                                } else {
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
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    snapchatCheck = false
                                } else {
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
        youtube.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.youtube = youtube.text.toString()
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    youtubeCheck = false
                                } else {
                                    youtubeCheck = true
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
        twitch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.twitch = twitch.text.toString()
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    twitchCheck = false
                                } else {
                                    twitchCheck = true
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
        pinterest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.pinterest = pinterest.text.toString()
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    pinterestCheck = false
                                } else {
                                    pinterestCheck = true
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
        tiktok.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.tiktok = tiktok.text.toString()
                userViewModel = ViewModelProvider(this@RegisterInf4).get(UserViewModel::class.java)
                userViewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    tiktokCheck = false
                                } else {
                                    tiktokCheck = true
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
            val influenceur = RegisterInfluenceurModel()
            influenceur.userPicture = RegisterInf2.registerProfilPictureInf
            if (arguments?.get("pseudo") != null) {
                influenceur.pseudo = arguments?.get("pseudo") as String
            }
            if (arguments?.get("email") != null) {
                influenceur.email = arguments?.get("email") as String
            }
            if (arguments?.get("password") != null) {
                influenceur.password = arguments?.get("password") as String
            }
            if (arguments?.get("userDescription") != null) {
                influenceur.userDescription = arguments?.get("userDescription") as String
            }
            if (arguments?.get("name") != null) {
                influenceur.fullName = arguments?.get("name") as String
            }
            if (arguments?.get("sexe") != null) {
                influenceur.sexe = arguments?.get("sexe") as String
            }
            if (arguments?.get("ville") != null) {
                influenceur.city = arguments?.get("ville") as String
            }
            if (arguments?.get("postal") != null) {
                influenceur.postal = arguments?.get("postal") as String
            }
            if (arguments?.get("phone") != null) {
                influenceur.phone = arguments?.get("phone") as String
            }
            if (facebook.text.toString().trim().isNotBlank() &&
                facebook.text.toString().trim().isNotEmpty()
            ) {
                influenceur.facebook = facebook.text.toString()
            } else {
                facebookCheck = true
            }
            if (twitter.text.toString().trim().isNotBlank() &&
                twitter.text.toString().trim().isNotEmpty()
            ) {
                influenceur.twitter = twitter.text.toString()
            } else {
                twitterCheck = true
            }
            if (instagram.text.toString().trim().isNotBlank() &&
                instagram.text.toString().trim().isNotEmpty()
            ) {
                influenceur.instagram = instagram.text.toString()
            } else {
                instagramCheck = true
            }
            if (snapchat.text.toString().trim().isNotBlank() &&
                snapchat.text.toString().trim().isNotEmpty()
            ) {
                influenceur.snapchat = snapchat.text.toString()
            } else {
                snapchatCheck = true
            }
            if (youtube.text.toString().trim().isNotBlank() &&
                youtube.text.toString().trim().isNotEmpty()
            ) {
                influenceur.youtube = youtube.text.toString()
            } else {
                youtubeCheck = true
            }
            if (twitch.text.toString().trim().isNotBlank() &&
                twitch.text.toString().trim().isNotEmpty()
            ) {
                influenceur.twitch = twitch.text.toString()
            } else {
                twitchCheck = true
            }
            if (pinterest.text.toString().trim().isNotBlank() &&
                pinterest.text.toString().trim().isNotEmpty()
            ) {
                influenceur.pinterest =
                    pinterest.text.toString()
            } else {
                pinterestCheck = true
            }
            if (tiktok.text.toString().trim().isNotBlank() &&
                tiktok.text.toString().trim().isNotEmpty()
            ) {
                influenceur.tiktok = tiktok.text.toString()
            } else {
                tiktokCheck = true
            }
//            var checkNetwork = true
//            if (!facebookCheck || !twitterCheck || !instagramCheck || !snapchatCheck || !youtubeCheck || !twitchCheck || !pinterestCheck || !tiktokCheck) {
//                Toast.makeText(context,
//                    "Veuillez modifier les réseaux sociaux contenant des erreurs",
//                    Toast.LENGTH_LONG).show()
//                checkNetwork = false
//            }
            if (!facebookCheck) {
                facebook.error = "Compte déjà utilisé"
            }
            if (!twitterCheck) {
                twitter.error = "Compte déjà utilisé"
            }
            if (!instagramCheck) {
                instagram.error = "Compte déjà utilisé"
            }
            if (!snapchatCheck) {
                snapchat.error = "Compte déjà utilisé"
            }
            if (!youtubeCheck) {
                youtube.error = "Compte déjà utilisé"
            }
            if (!tiktokCheck) {
                tiktok.error = "Compte déjà utilisé"
            }
            if (!pinterestCheck) {
                pinterest.error = "Compte déjà utilisé"
            }
            if (!twitchCheck) {
                twitch.error = "Compte déjà utilisé"
            }
            if (themeState != 0) {
                influenceur.theme = themeState.toString()
                viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
                viewModel.registerInfluencer(registerInfluenceurModel = influenceur)
                    .observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    Log.i(
                                        "Inscription",
                                        "Influenceur ${influenceur.pseudo} inscrit"
                                    )
                                    findNavController().navigate(R.id.navigation_login_inf)
                                }
                                Status.ERROR -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    Log.e(
                                        "Inscription",
                                        "Echec inscription influenceur ${it.message}"
                                    )
                                }
                            }
                        }
                    })
            } else if (themeState == 0) {
                Toast.makeText(context, "Un thème doit être choisi", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
