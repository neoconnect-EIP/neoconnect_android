package c.eip.neoconnect.ui.view.report

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
import c.eip.neoconnect.data.model.report.OffreReportModel
import c.eip.neoconnect.data.model.report.UserReportModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class Report : Fragment() {
    private lateinit var offresViewModel: OffresViewModel
    private lateinit var userViewModel: UserViewModel
    private var reportState: Int = 0

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Initialisation d'une liste déroulante
     * Mise en place du fond selon Influenceur ou Marque
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  
        val inflate = inflater.inflate(R.layout.fragment_report, container, false)
        var reportList: Array<String> = emptyArray()
        if (arguments?.get("type") == "offre") {
            reportList = resources.getStringArray(R.array.offerReportSpinner)
        } else if (arguments?.get("type") == "user") {
            reportList = resources.getStringArray(R.array.userReportSpinner)
        }
        val spinner = inflate.findViewById<Spinner>(R.id.reportSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.layout_spinner_item, reportList)
            spinner.adapter = adapter
        }
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            spinner.setPopupBackgroundResource(R.color.spinnerInf)
            inflate.findViewById<ConstraintLayout>(R.id.reportLayout)
                .setBackgroundResource(R.drawable.background_influencer)
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            spinner.setPopupBackgroundResource(R.color.spinnerShop)
            inflate.findViewById<ConstraintLayout>(R.id.reportLayout)
                .setBackgroundResource(R.drawable.background_shop)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                reportState = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
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
        view.findViewById<ImageView>(R.id.reportButton).setOnClickListener {
            val token = DataGetter.INSTANCE.getToken(requireContext())
            if (arguments?.get("type") == "offre") {
                reportOffer(token!!, arguments?.get("offerId") as Int, view)
            } else if (arguments?.get("type") == "user") {
                reportUser(token!!, arguments?.get("userId") as String, view)
            }

        }
    }

    /**
     * Signaler une offre
     */
    private fun reportOffer(token: String, offerId: Int, view: View) {
        val reportList = resources.getStringArray(R.array.offerReportSpinner)
        val reportOffer = OffreReportModel()
        reportOffer.subject = reportList[reportState]
        reportOffer.message =
            view.findViewById<TextInputEditText>(R.id.reportMessage).text.toString()
        reportOffer.offreName = arguments?.get("name") as String
        offresViewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        offresViewModel.reportOffer(token = token, id = offerId, report = reportOffer)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Report Offer", it.message!!)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Report Offer", it.message!!)
                        }
                    }
                }
            })
    }

    /**
     * Signaler un utilisateur
     */
    private fun reportUser(token: String, offerId: String, view: View) {
        val reportList = resources.getStringArray(R.array.userReportSpinner)
        val reportUser = UserReportModel()
        reportUser.subject = reportList[reportState]
        reportUser.message =
            view.findViewById<TextInputEditText>(R.id.reportMessage).text.toString()
        reportUser.pseudo = arguments?.get("name") as String
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.reportUser(token = token, id = offerId.toInt(), report = reportUser)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Report User", it.message!!)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("Report User", it.message!!)
                        }
                    }
                }
            })
    }
}