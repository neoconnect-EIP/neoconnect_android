package c.eip.neoconnect.ui.view.profil

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.ChatViewModel
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class ProfilOtherInf : Fragment() {
    private lateinit var viewModel: InfViewModel
    private lateinit var chatViewModel: ChatViewModel
    val bundle = bundleOf()
    private var name: String? = ""

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_other_inf, container, false)
        when (arguments?.get("mode")) {
            0 -> {
                getOtherProfilInf(inflate = inflate)
            }
            1 -> {
                resultSearchProfilInf(inflate = inflate)
            }
            else -> {
                findNavController().popBackStack()
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
        view.findViewById<TextView>(R.id.contactUser).setOnClickListener {
            val mDialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_chat, null)
            val mAlertDialogBuilder =
                AlertDialog.Builder(requireContext()).setView(mDialogView)
                    .setTitle("Comment voulez-vous le contacter ?")
            val mAlertDialog = mAlertDialogBuilder.show()
            mDialogView.findViewById<TextView>(R.id.byMailButton).setOnClickListener {
                mAlertDialog.dismiss()
                findNavController().navigate(R.id.navigation_contact_user, bundle)
            }
            mDialogView.findViewById<TextView>(R.id.byPrivateMessageButton).setOnClickListener {
                mAlertDialog.dismiss()
                sendPrivateMessage()
            }
        }
        view.findViewById<TextView>(R.id.otherInfStats).setOnClickListener {
            findNavController().navigate(
                R.id.navigation_stats,
                bundleOf("profil" to "other", "id" to arguments?.get("id"))
            )
        }
        view.findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            settingsOption(view = view)
        }
    }

    /**
     * Récupération du profil Influenceur
     */
    private fun getOtherProfilInf(inflate: View) {
        val id = arguments?.get("id") as Int
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        viewModel.getOtherInf(token = token!!, id = id).observe(viewLifecycleOwner, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.userPicture?.size!! <= 0) {
                            inflate.findViewById<ImageView>(R.id.otherInfPicture)
                                .setImageResource(R.drawable.ic_picture_inf)
                        } else {
                            Glide.with(requireContext())
                                .load(it.data.userPicture[0]?.imageData)
                                .circleCrop().error(R.drawable.ic_picture_inf)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(inflate.findViewById(R.id.otherInfPicture))
                        }
                        name = it.data.pseudo
                        inflate.findViewById<TextView>(R.id.otherInfPseudo).text =
                            it.data.pseudo
                        inflate.findViewById<TextView>(R.id.otherInfDescription).text =
                            it.data.userDescription
                        val theme = "Thème : " + it.data.theme
                        inflate.findViewById<TextView>(R.id.otherInfSubject).text = theme
                        val average = inflate.findViewById<TextView>(R.id.otherInfAverage)
                        if (it.data.average.isNullOrBlank()) {
                            average.setText(R.string.noNotes)
                            inflate.findViewById<TextView>(R.id.otherInfStats).visibility =
                                View.GONE
                        } else {
                            val averageNb = String.format("%.2f", it.data.average!!.toFloat())
                            val note = "Note : $averageNb/5★"
                            average.text = note
                        }
                        val nbOfferApplied = "${it.data.nbOfferApplied}\n offres en cours"
                        inflate.findViewById<TextView>(R.id.otherInfNbOffers).text = nbOfferApplied
                        bundle.putString("dest", it.data.email)
                    }
                    Status.ERROR -> {
                        Log.e("Get Inf", "$id introuvable")
                        Toast.makeText(
                            context,
                            "Utilisateur introuvable",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }

    /**
     * Récupération du profil Influenceur par recherche
     */
    private fun resultSearchProfilInf(inflate: View) {
        if (Search.searchResponse?.userPicture?.size!! <= 0) {
            inflate.findViewById<ImageView>(R.id.otherInfPicture)
                .setImageResource(R.drawable.ic_picture_inf)
        } else {
            Glide.with(requireContext())
                .load(Search.searchResponse?.userPicture?.get(0)?.imageData)
                .circleCrop().error(R.drawable.ic_picture_inf)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(inflate.findViewById(R.id.otherInfPicture))
        }
        name = Search.searchResponse?.pseudo
        inflate.findViewById<TextView>(R.id.otherInfPseudo).text =
            Search.searchResponse?.pseudo
        val theme = "Thème : " + Search.searchResponse?.theme
        inflate.findViewById<TextView>(R.id.otherInfSubject).text = theme
        val average = inflate.findViewById<TextView>(R.id.otherInfAverage)
        if (Search.searchResponse?.average == null) {
            average.setText(R.string.noNotes)
            inflate.findViewById<TextView>(R.id.otherInfStats).visibility =
                View.GONE
        } else {
            val averageNb = String.format("%.2f", Search.searchResponse?.average!!.toFloat())
            val note = "Note : $averageNb/5★"
            average.text = note
        }
        inflate.findViewById<TextView>(R.id.otherInfDescription).text =
            Search.searchResponse?.userDescription
        val nbOfferApplied = "${Search.searchResponse?.nbOfferApplied}\n offres en cours"
        inflate.findViewById<TextView>(R.id.otherInfNbOffers).text = nbOfferApplied
        bundle.putString("dest", Search.searchResponse?.email)
    }

    /**
     * Contacter par message privé
     */
    private fun sendPrivateMessage() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_first_message, null)
        val mAlertDialogBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView)
            .setTitle("Envoyer un message")
        val mAlertDialog = mAlertDialogBuilder.show()
        mDialogView.findViewById<ImageView>(R.id.sendFirstMessageButton).setOnClickListener {
            chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
            val token = DataGetter.INSTANCE.getToken(requireContext())
            val message = MessageModel()
            message.message =
                mDialogView.findViewById<TextInputEditText>(R.id.insertFirstMessage).text.toString()
            if (arguments?.get("mode") == 0) {
                message.userId = (arguments?.get("id") as Int).toString()
            } else {
                message.userId = Search.searchResponse?.id.toString()
            }
            chatViewModel.postMessage(token = token!!, message = message)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    context,
                                    "Message envoyé",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mAlertDialog.dismiss()
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Impossible d'envoyer votre message",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("One Chat", it.message!!)
                            }
                        }
                    }
                })
        }
    }

    /**
     * Option pour le profil (Noter, Signaler)
     */
    private fun settingsOption(view: View) {
        val popupMenu = PopupMenu(context, view.findViewById(R.id.settingsButton))
        popupMenu.menuInflater.inflate(R.menu.other_profil_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.markUser -> {
                    val bundleMark = bundleOf()
                    if (arguments?.get("mode") == 0) {
                        bundleMark.putString("userId", (arguments?.get("id") as Int).toString())
                    } else {
                        bundleMark.putString("userId", (Search.searchResponse?.id.toString()))
                    }
                    findNavController().navigate(R.id.navigation_mark_user, bundleMark)
                }
                R.id.reportUser -> {
                    val bundleReport = bundleOf("type" to "user", "name" to name)
                    if (arguments?.get("mode") == 0) {
                        bundleReport.putString("userId", (arguments?.get("id") as Int).toString())
                    } else {
                        bundleReport.putString("userId", (Search.searchResponse?.id.toString()))
                    }
                    findNavController().navigate(R.id.navigation_report, bundleReport)
                }
            }
            true
        }
        popupMenu.show()
    }
}
