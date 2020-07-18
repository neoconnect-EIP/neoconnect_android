package c.eip.neoconnect.ui.view.forgotPassword


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordThirdStepModel
import c.eip.neoconnect.ui.viewModel.ResetPasswordViewModel
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class UpdatePassword : Fragment() {
    private lateinit var viewModel: ResetPasswordViewModel
    private val checkInput = CheckInput()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<TextInputEditText>(R.id.updatePasswordToken)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val token = view.findViewById<TextInputEditText>(R.id.updatePasswordToken)
                        .text.toString()
                    if (token.isBlank()) {
                        Toast.makeText(context, "Veuillez renseigner le token", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        checkToken(token)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
        view.findViewById<TextView>(R.id.updatePasswordButton).setOnClickListener {
            val thirdStep = ResetPasswordThirdStepModel()
            thirdStep.email = arguments?.get("email") as String
            thirdStep.token =
                view.findViewById<TextInputEditText>(R.id.updatePasswordToken).text.toString()
            val password =
                view.findViewById<TextInputEditText>(R.id.updatePasswordNewPassword).text.toString()
            val passwordCheck =
                view.findViewById<TextInputEditText>(R.id.updatePasswordNewPasswordConfirm).text.toString()
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
            if (checkPassword == 0) {
                thirdStep.password = password
                viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
                viewModel.updatePassword(thirdStep).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                Log.e("Update password", it.message)
                                findNavController().navigate(R.id.navigation_login_inf)
                            }
                            Status.ERROR -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.e("Update password", it.message)
                            }
                        }

                    }
                })
            }
        }
    }

    fun checkToken(token: String) {
        viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        viewModel.checkToken(token).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        view?.findViewById<TextInputEditText>(R.id.updatePasswordNewPassword)?.visibility =
                            View.VISIBLE
                        view?.findViewById<TextInputEditText>(R.id.updatePasswordNewPasswordConfirm)?.visibility =
                            View.VISIBLE
                        view?.findViewById<Button>(R.id.updatePasswordButton)?.visibility =
                            View.VISIBLE
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Check Token", it.message)
                    }
                }
            }
        })
    }
}
