package c.eip.neoconnect.ui.view.register.influencer


import android.os.Bundle
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
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.ui.viewModel.RegisterViewModel
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class RegisterInf3 : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private var themeState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_inf_3, container, false)
        val themeList = resources.getStringArray(R.array.themeSpinner)
        val themeSpinner = inflate.findViewById<Spinner>(R.id.themeSpinner)
        if (themeSpinner != null) {
            val themeAdapter = ArrayAdapter(requireContext(), R.layout.layout_spinner_item, themeList)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_register_inf_2, arguments)
        }
        view.findViewById<Button>(R.id.endRegisterButton).setOnClickListener {
            val influenceur = RegisterInfluenceurModel()
            influenceur.userPicture = RegisterInf1.registerProfilPictureInf
            if (arguments?.get("pseudo") != null) {
                influenceur.pseudo = arguments?.get("pseudo") as String
            }
            if (arguments?.get("email") != null) {
                influenceur.email = arguments?.get("email") as String
            }
            if (arguments?.get("password") != null) {
                influenceur.password = arguments?.get("password") as String
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
            if (view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.facebook =
                    view.findViewById<TextInputEditText>(R.id.registerFacebook).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.twitter =
                    view.findViewById<TextInputEditText>(R.id.registerTwitter).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.instagram =
                    view.findViewById<TextInputEditText>(R.id.registerInstagram).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.snapchat =
                    view.findViewById<TextInputEditText>(R.id.registerSnapchat).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerYoutube).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerYoutube).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.youtube =
                    view.findViewById<TextInputEditText>(R.id.registerYoutube).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerTwitch).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerTwitch).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.twitch =
                    view.findViewById<TextInputEditText>(R.id.registerTwitch).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerPinterest).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerPinterest).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.pinterest =
                    view.findViewById<TextInputEditText>(R.id.registerPinterest).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.registerTiktok).text.toString().trim()
                    .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerTiktok).text.toString()
                    .trim().isNotEmpty()
            ) {
                influenceur.tiktok =
                    view.findViewById<TextInputEditText>(R.id.registerTiktok).text.toString()
            }
            if (themeState != 0) {
                val themeList = resources.getStringArray(R.array.themeSpinner)
                influenceur.theme = themeList[themeState]
            }
            viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
            viewModel.registerInfluencer(influenceur).observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Inscription", "Influenceur ${influenceur.pseudo} inscrit")
                            findNavController().navigate(R.id.navigation_login_inf)
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Inscription", "Echec inscription influenceur ${it.message}")
                        }
                    }
                }
            })
        }
    }
}
