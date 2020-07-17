package c.eip.neoconnect.ui.view.contact


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.MainViewInf
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.ui.viewModel.ContactViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class ContactUser : Fragment() {
    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_contact, container, false)
        if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.contactUsLayout)
                .setBackgroundResource(R.drawable.background_shop)
            inflate.findViewById<TextInputEditText>(R.id.sendMailPseudo).visibility =
                View.GONE
            inflate.findViewById<TextInputEditText>(R.id.sendMailEmail).visibility =
                View.GONE
        } else if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.contactUsLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            inflate.findViewById<TextInputEditText>(R.id.sendMailPseudo).visibility =
                View.GONE
            inflate.findViewById<TextInputEditText>(R.id.sendMailEmail).visibility =
                View.GONE
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<TextView>(R.id.sendMailButton).setOnClickListener {
            val contactModel = ContactUserModel()
            if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
                contactModel.pseudo = MainViewShop.shopData?.pseudo
                contactModel.email = MainViewShop.shopData?.email
            } else if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
                contactModel.pseudo = MainViewInf.influenceurData?.pseudo
                contactModel.email = MainViewInf.influenceurData?.email
            }
            contactModel.dest = arguments?.get("dest") as String
            contactModel.objet =
                view.findViewById<TextInputEditText>(R.id.sendMailSujet).text.toString()
            contactModel.message =
                view.findViewById<TextInputEditText>(R.id.sendMailMessage).text.toString()
            viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
            viewModel.contactUser(contactModel).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            Log.e("Contact user", it.message)
                        }
                    }

                }
            })
        }
    }
}