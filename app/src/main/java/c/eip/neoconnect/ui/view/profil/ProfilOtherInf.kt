package c.eip.neoconnect.ui.view.profil

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_other_inf, container, false)
        when {
            arguments?.get("mode") == 0 -> {
                val id = arguments?.get("id") as Int
                val token = DataGetter.INSTANCE.getToken(requireContext())
                viewModel = ViewModelProvider(this).get(InfViewModel::class.java)
                viewModel.getOtherInf(token!!, id).observe(viewLifecycleOwner, Observer {
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data?.userPicture?.size!! <= 0) {
                                    inflate.findViewById<ImageView>(R.id.otherProfilPicture)
                                        .setImageResource(R.drawable.ic_picture_inf)
                                } else {
                                    Glide.with(requireContext())
                                        .load(it.data.userPicture[0]?.imageData)
                                        .circleCrop().error(R.drawable.ic_picture_inf)
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(inflate.findViewById(R.id.otherProfilPicture))
                                }
                                inflate.findViewById<TextView>(R.id.otherProfilPseudo).text =
                                    it.data.pseudo
                                inflate.findViewById<TextView>(R.id.otherProfilSubject).text =
                                    it.data.theme
                                if (it.data.average.isNullOrBlank()) {
                                    inflate.findViewById<TextView>(R.id.otherProfilAverage).text =
                                        "0"
                                } else {
                                    inflate.findViewById<TextView>(R.id.otherProfilAverage).text =
                                        it.data.average
                                }
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
            arguments?.get("mode") == 1 -> {
                if (Search.searchResponse?.userPicture?.size!! <= 0) {
                    inflate.findViewById<ImageView>(R.id.otherProfilPicture)
                        .setImageResource(R.drawable.ic_picture_inf)
                } else {
                    Glide.with(requireContext())
                        .load(Search.searchResponse?.userPicture?.get(0)?.imageData)
                        .circleCrop().error(R.drawable.ic_picture_inf)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(inflate.findViewById(R.id.otherProfilPicture))
                }
                inflate.findViewById<TextView>(R.id.otherProfilPseudo).text =
                    Search.searchResponse?.pseudo
                inflate.findViewById<TextView>(R.id.otherProfilSubject).text =
                    Search.searchResponse?.theme
                inflate.findViewById<TextView>(R.id.otherProfilAverage).text =
                    Search.searchResponse?.average.toString()
                bundle.putString("dest", Search.searchResponse?.email)
            }
            else -> {
                findNavController().popBackStack()
            }
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.contactByMailProfilButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_contact, bundle)
        }
        view.findViewById<Button>(R.id.contactByMPProfilButton).setOnClickListener {
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
                chatViewModel.postMessage(token!!, message).observe(viewLifecycleOwner, Observer {
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
                                Log.e("One Chat", it.message)
                            }
                        }
                    }
                })
            }
        }
        view.findViewById<Button>(R.id.markProfilButton).setOnClickListener {
            val bundleMark = bundleOf()
            if (arguments?.get("mode") == 0) {
                bundleMark.putString("userId", (arguments?.get("id") as Int).toString())
            } else {
                bundleMark.putString("userId", (Search.searchResponse?.id.toString()))
            }
            findNavController().navigate(R.id.navigation_mark_user, bundleMark)
        }
    }
}
