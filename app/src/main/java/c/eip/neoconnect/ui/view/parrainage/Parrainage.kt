package c.eip.neoconnect.ui.view.parrainage

import android.app.AlertDialog
import android.os.Bundle
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
import c.eip.neoconnect.data.model.parrainage.ParrainageModel
import c.eip.neoconnect.ui.view.feed.FeedInf
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class Parrainage : Fragment() {
    private lateinit var userviewModel: UserViewModel
    private lateinit var infViewModel: InfViewModel
    private var infData = FeedInf.influenceurData

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Affichage du code de parrainage + nombres parrainés
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_parrainage, container, false)
        inflate.findViewById<TextView>(R.id.parrainageCodeInf).text = infData?.codeParrainage
        val nbParraines: String = if (infData?.countParrainage != null) {
            "Vous avez parrainé 0 personne"
        } else {
            "Vous avez parrainé ${infData?.countParrainage} personne"
        }
        inflate.findViewById<TextView>(R.id.nbParrainesInf).text = nbParraines
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
        val token = DataGetter.INSTANCE.getToken(requireContext())
        infViewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        infViewModel.getProfilInf(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val nbParraines: String = if (it.data?.countParrainage == null) {
                            "Vous avez parrainé 0 personne"
                        } else {
                            "Vous avez parrainé ${it.data.countParrainage} personnes"
                        }
                        view.findViewById<TextView>(R.id.nbParrainesInf).text = nbParraines
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Impossible de mettre à jour votre nombre de parrainés",
                            Toast.LENGTH_LONG
                        )
                    }
                }
            }
        })
        view.findViewById<TextView>(R.id.inputButtonParraines).setOnClickListener {
            val mDialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_parrain, null)
            val mAlertDialogBuilder =
                AlertDialog.Builder(requireContext()).setView(mDialogView)
            val mAlertDialog = mAlertDialogBuilder.show()
            mDialogView.findViewById<TextView>(R.id.sendCodeParrain).setOnClickListener {
                if (mDialogView.findViewById<TextView>(R.id.insertCodeParrain).text.toString() == infData?.codeParrainage) {
                    Toast.makeText(requireContext(),
                        "Vous ne pouvez pas entrer votre code",
                        Toast.LENGTH_LONG).show()
                } else if (infData?.isParraine == true) {
                    Toast.makeText(requireContext(),
                        "Vous avez déjà été parrainé",
                        Toast.LENGTH_LONG).show()
                } else {
                    sendCodeParrain(
                        code = mDialogView.findViewById<TextView>(R.id.insertCodeParrain).text.toString(),
                        mAlertDialog = mAlertDialog
                    )
                }
            }
        }
    }

    /**
     * Ajouter un code de parrainage
     */
    private fun sendCodeParrain(code: String, mAlertDialog: AlertDialog) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        userviewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val codeForm = ParrainageModel()
        codeForm.codeParrainage = code
        userviewModel.insertCodeParrainage(token = token!!, code = codeForm)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mAlertDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            val fragment = Parrainage()
                            fragment.arguments = arguments
                            fragmentManager?.beginTransaction()
                                ?.replace(requireView().id, fragment)?.commit()
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Send Code Parrainage", it.message.toString())
                        }
                    }
                }
            })
    }
}