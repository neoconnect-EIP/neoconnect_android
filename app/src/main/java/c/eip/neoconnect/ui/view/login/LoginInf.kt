package c.eip.neoconnect.ui.view.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.ui.viewModel.LoginViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class LoginInf : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_inf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.switchToShopButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_login_shop)
        }
        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            val bundle = bundleOf()
            findNavController().navigate(R.id.navigation_register_inf, bundle)
        }
        view.findViewById<Button>(R.id.forgotPassword).setOnClickListener {
            findNavController().navigate(R.id.navigation_password_forgot)
        }
        view.findViewById<Button>(R.id.connectButton).setOnClickListener {
            val checkPseudo: Boolean =
                if (view.findViewById<TextInputEditText>(R.id.loginPseudo).text.toString().trim()
                        .isNotBlank() && view.findViewById<TextInputEditText>(R.id.loginPseudo).text.toString()
                        .trim().isNotEmpty()
                ) {
                    true
                } else {
                    Toast.makeText(
                        context,
                        "Le pseudo ne doit pas être vide et doit contenir entre 4 et 12 caractères",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                }

            val checkPassword: Boolean =
                if (view.findViewById<TextInputEditText>(R.id.loginPassword).text.toString().trim()
                        .isNotBlank() && view.findViewById<TextInputEditText>(R.id.loginPassword).text.toString()
                        .trim().isNotEmpty()
                ) {
                    true
                } else {
                    Toast.makeText(
                        context,
                        "Le mot de passe ne doit pas être vide, doit contenir entre 4 et 12 caractères avec 1 minuscule, 1 majuscule et 1 chiffre",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                }

            if (checkPassword && checkPseudo) {
                val pseudo = view.findViewById<TextInputEditText>(R.id.loginPseudo).text.toString()
                val password =
                    view.findViewById<TextInputEditText>(R.id.loginPassword).text.toString()
                val loginModel = LoginModel(pseudo, password)
                viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
                viewModel.login(loginModel).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                Log.i(
                                    "Connexion",
                                    "${it.data?.userType} ${it.data?.idUser} connecté"
                                )
                                DataGetter.INSTANCE.saveToken(requireContext(), it.data?.token)
                                DataGetter.INSTANCE.saveUserId(requireContext(), it.data?.idUser)
                                DataGetter.INSTANCE.saveUserType(requireContext(), it.data?.userType)
                                when (it.data?.userType) {
                                    "shop" -> {
                                        findNavController().navigate(R.id.navigation_main_view_shop)
                                    }
                                    "influencer" -> {
                                        findNavController().navigate(R.id.navigation_main_view_inf)
                                    }
                                    else -> {
                                        Log.i("Connexion", "Connexion en tant que 3ème partie")
                                    }
                                }
                            }
                            Status.ERROR -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.e("Connexion", it.message)
                            }
                        }
                    }
                })
            }

        }
    }
}
