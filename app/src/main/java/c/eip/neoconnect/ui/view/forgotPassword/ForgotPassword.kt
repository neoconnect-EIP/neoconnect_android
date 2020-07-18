package c.eip.neoconnect.ui.view.forgotPassword


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.ui.viewModel.ResetPasswordViewModel
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class ForgotPassword : Fragment() {
    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<TextView>(R.id.resetPasswordButton).setOnClickListener {
            val checkEmail: Boolean =
                if (view.findViewById<TextInputEditText>(R.id.resetPasswordEmail).text.toString()
                        .trim()
                        .isNotBlank() && view.findViewById<TextInputEditText>(R.id.resetPasswordEmail).text.toString()
                        .trim().isNotEmpty()
                ) {
                    true
                } else {
                    Toast.makeText(
                        context,
                        "L'adresse mail ne doit pas être vide",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                }
            if (checkEmail) {
                val firstStep = ResetPasswordFirstStepModel()
                firstStep.email =
                    view.findViewById<TextInputEditText>(R.id.resetPasswordEmail).text.toString()
                viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
                viewModel.forgotPassword(firstStep).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                Log.i("Reset password", "Email envoyé à ${firstStep.email}")
                                val bundle = bundleOf("email" to firstStep.email)
                                findNavController().navigate(R.id.navigation_password_update, bundle)
                            }
                            Status.ERROR -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.e("Reset password", it.message)
                            }
                        }
                    }
                })
            }
        }
    }
}
