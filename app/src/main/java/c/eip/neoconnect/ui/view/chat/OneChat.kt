package c.eip.neoconnect.ui.view.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.ui.adapter.OneChatAdapter
import c.eip.neoconnect.ui.viewModel.ChatViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText


class OneChat : Fragment() {
    private lateinit var viewModel: ChatViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_one_chat, container, false)
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.oneChatLayout)
                .setBackgroundResource(R.drawable.background_shop)
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.oneChatLayout)
                .setBackgroundResource(R.drawable.background_influencer)
        }
        val title = "Retour - " + arguments?.get("pseudo") as String
        inflate.findViewById<TextView>(R.id.titleOneChat).text = title
        getCanal(view = inflate)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     * Actualisation des messages
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.titleOneChat).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<ImageView>(R.id.refreshMessage).setOnClickListener {
            val fragment = OneChat()
            fragment.arguments = arguments
            fragmentManager?.beginTransaction()
                ?.replace(view.id, fragment)?.commit()
        }

        view.findViewById<ImageView>(R.id.sendChatMessageButton).setOnClickListener {
            sendMessage(view = view)
        }
    }

    /**
     * Récupération des messages
     */
    private fun getCanal(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        viewModel.getOneChannel(token = token!!, id = arguments?.get("id") as Int)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            val recyclerOneChat =
                                view.findViewById<RecyclerView>(R.id.recyclerOneChat)
                            recyclerOneChat.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                ).apply {
                                    stackFromEnd = true
                                    isSmoothScrollbarEnabled = true
                                }
                            val userId = DataGetter.INSTANCE.getUserId(requireContext())
                            val adapter = OneChatAdapter(
                                it.data!!,
                                userId
                            )
                            userSrc = it.data.user_1
                            userDest = it.data.user_2
                            adapter.notifyDataSetChanged()
                            recyclerOneChat.adapter = adapter
                            recyclerOneChat.scrollToPosition(adapter.itemCount - 1)
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                context,
                                "Impossible de récupérer vos messages",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                            Log.e("One Chat", it.message!!)
                        }
                    }
                }
            })
    }

    /**
     * Envoi de message
     */
    private fun sendMessage(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val userId = DataGetter.INSTANCE.getUserId(requireContext())
        val messageModel = MessageModel()
        messageModel.message =
            view.findViewById<TextInputEditText>(R.id.insertChatMessage).text.toString()
        if (userSrc == userId.toString()) {
            messageModel.userId = userDest
        } else {
            messageModel.userId = userSrc
        }
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        viewModel.postMessage(token = token!!, message = messageModel)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            val fragment = OneChat()
                            fragment.arguments = arguments
                            fragmentManager?.beginTransaction()
                                ?.replace(view.id, fragment)?.commit()
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

    companion object {
        var userSrc: String? = ""
        var userDest: String? = ""
    }
}