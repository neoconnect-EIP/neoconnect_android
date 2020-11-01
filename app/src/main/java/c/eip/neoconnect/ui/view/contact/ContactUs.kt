package c.eip.neoconnect.ui.view.contact


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.FeedbackModel
import c.eip.neoconnect.ui.view.feed.FeedInf
import c.eip.neoconnect.ui.view.feed.FeedShop
import c.eip.neoconnect.ui.viewModel.ContactViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class ContactUs : Fragment() {
    private lateinit var viewModel: ContactViewModel
    private var spinnerState: Int = 3

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     * Initialisation de la liste déroulante
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_contact, container, false)
        val contactList = resources.getStringArray(R.array.contactSpinner)
        val spinner = inflate.findViewById<Spinner>(R.id.contactSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.layout_spinner_item, contactList)
            spinner.adapter = adapter
        }
        spinner.setSelection(spinnerState)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerState = position
                when (position) {
                    0 -> {
                        inflate.findViewById<TextInputEditText>(R.id.sendMailMessage).hint =
                            getString(R.string.formFeature)
                        inflate.findViewById<TextInputEditText>(R.id.sendMailSujet).visibility =
                            View.GONE
                    }
                    1 -> {
                        inflate.findViewById<TextInputEditText>(R.id.sendMailMessage).hint =
                            getString(R.string.formFeature)
                        inflate.findViewById<TextInputEditText>(R.id.sendMailSujet).visibility =
                            View.GONE
                    }
                    2 -> {
                        inflate.findViewById<TextInputEditText>(R.id.sendMailMessage).hint =
                            getString(R.string.formCommentaire)
                        inflate.findViewById<TextInputEditText>(R.id.sendMailSujet).visibility =
                            View.GONE
                    }
                    3 -> inflate.findViewById<TextInputEditText>(R.id.sendMailMessage).hint =
                        getString(R.string.formMessage)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        if (!DataGetter.INSTANCE.getUserType(requireContext()).isNullOrBlank()) {
            if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
                inflate.findViewById<ConstraintLayout>(R.id.contactUsLayout)
                    .setBackgroundResource(R.drawable.background_shop)
                inflate.findViewById<TextInputEditText>(R.id.sendMailPseudo).visibility =
                    View.GONE
                inflate.findViewById<TextInputEditText>(R.id.sendMailEmail).visibility =
                    View.GONE
            } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
                inflate.findViewById<ConstraintLayout>(R.id.contactUsLayout)
                    .setBackgroundResource(R.drawable.background_influencer)
                inflate.findViewById<TextInputEditText>(R.id.sendMailPseudo).visibility =
                    View.GONE
                inflate.findViewById<TextInputEditText>(R.id.sendMailEmail).visibility =
                    View.GONE
            }
        } else {
            inflate.findViewById<ConstraintLayout>(R.id.contactUsLayout)
                .setBackgroundResource(R.drawable.background_selection_side)
            inflate.findViewById<TextInputEditText>(R.id.sendMailPseudo).visibility =
                View.VISIBLE
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
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
            findNavController().popBackStack()
        }
        view.findViewById<ImageView>(R.id.sendMailButton).setOnClickListener {
            if (spinnerState == 3) {
                contactUs(view)
            } else if (spinnerState == 0 || spinnerState == 1 || spinnerState == 2) {
                sendFeedback(view)
            }
        }
    }

    /**
     * Envoi de message à contact.neoconnect@gmail.com
     */
    private fun contactUs(view: View) {
        val contactModel = ContactModel()
        if (!DataGetter.INSTANCE.getUserType(requireContext()).isNullOrBlank()) {
            if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
                contactModel.pseudo = FeedShop.shopData?.pseudo
                contactModel.email = FeedShop.shopData?.email
            } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
                contactModel.pseudo = FeedInf.influenceurData?.pseudo
                contactModel.email = FeedInf.influenceurData?.email
            }
        } else {
            contactModel.pseudo =
                view.findViewById<TextInputEditText>(R.id.sendMailPseudo).text.toString()
            contactModel.email =
                view.findViewById<TextInputEditText>(R.id.sendMailEmail).text.toString()
        }
        contactModel.objet =
            view.findViewById<TextInputEditText>(R.id.sendMailSujet).text.toString()
        contactModel.message =
            view.findViewById<TextInputEditText>(R.id.sendMailMessage).text.toString()
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.contactUs(contact = contactModel).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Contact us", it.message!!)
                    }
                }
            }
        })
    }

    /**
     * Envoi de retour utilisateur
     */
    private fun sendFeedback(view: View) {
        val feedback = FeedbackModel()
        feedback.environnement = "android"
        val message = view.findViewById<TextInputEditText>(R.id.sendMailMessage).text.toString()
        when (spinnerState) {
            0 -> {
                feedback.type = "bug"
                feedback.fonctionnalite = message
                feedback.commentaire = null
            }
            1 -> {
                feedback.type = "amelioration"
                feedback.fonctionnalite = message
                feedback.commentaire = null
            }
            2 -> {
                feedback.type = "commentaire"
                feedback.commentaire = message
                feedback.fonctionnalite = null
            }
        }
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.sendFeedback(message = feedback).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Feedback", it.message!!)
                    }
                }
            }
        })
    }
}
