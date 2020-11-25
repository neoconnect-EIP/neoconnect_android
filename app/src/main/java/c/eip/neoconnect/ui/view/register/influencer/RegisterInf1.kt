package c.eip.neoconnect.ui.view.register.influencer


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import c.eip.neoconnect.data.model.register.CheckFieldModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class RegisterInf1 : Fragment() {
    private lateinit var viewModel: UserViewModel
    private val checkInput = CheckInput()
    val bundle = bundleOf()
    private var pseudoCheck = false
    private var emailCheck = false

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_inf_1, container, false)
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
            findNavController().navigate(R.id.navigation_login_inf)
        }
        val pseudo = view.findViewById<TextInputEditText>(R.id.registerPseudo)
        val email = view.findViewById<TextInputEditText>(R.id.registerEmail)
        pseudo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.pseudo = pseudo.text.toString()
                viewModel = ViewModelProvider(this@RegisterInf1).get(UserViewModel::class.java)
                viewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    pseudo.error = "Ce pseudo est déjà pris"
                                    pseudoCheck = false
                                } else {
                                    pseudo.error = null
                                    pseudoCheck = true
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
        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val field = CheckFieldModel()
                field.email = email.text.toString()
                viewModel = ViewModelProvider(this@RegisterInf1).get(UserViewModel::class.java)
                viewModel.checkField(field = field).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data == true) {
                                    email.error = "Cette adresse mail est déjà prise"
                                    emailCheck = false
                                } else {
                                    email.error = null
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

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        view.findViewById<Button>(R.id.nextPageRegister).setOnClickListener {
            val password =
                view.findViewById<TextInputEditText>(R.id.registerPassword)?.text.toString()
            val passwordCheck =
                view.findViewById<TextInputEditText>(R.id.registerPasswordConfirm)?.text.toString()
            val checkPseudo = checkInput.checkPseudo(pseudo.text.toString())
            if (!checkPseudo) {
                Toast.makeText(
                    context,
                    "Le pseudo ne doit pas être vide et contenir entre 4 et 12 caractères",
                    Toast.LENGTH_LONG
                ).show()
            }
            val checkEmail = checkInput.checkEmail(email.text.toString())
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
            if (!pseudoCheck) {
                Toast.makeText(context, "Ce pseudo est déjà utilisé", Toast.LENGTH_LONG).show()
            }
            if (!emailCheck) {
                Toast.makeText(context, "Cette adresse mail est déjà utilisé", Toast.LENGTH_LONG).show()
            }
            if (pseudoCheck && emailCheck && checkPseudo && checkEmail && checkPassword == 0) {
                bundle.putString("pseudo", pseudo.text.toString())
                bundle.putString("password", password)
                bundle.putString("email", email.text.toString())
                findNavController().navigate(R.id.navigation_register_inf_2, bundle)
            }
        }
    }
}
