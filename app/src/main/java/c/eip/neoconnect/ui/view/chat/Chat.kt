package c.eip.neoconnect.ui.view.chat


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.ChatAdapter
import c.eip.neoconnect.ui.viewModel.ChatViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class Chat : Fragment() {
    private lateinit var viewModel: ChatViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_chat, container, false)
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.chatLayout)
                .setBackgroundResource(R.drawable.background_shop)
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.chatLayout)
                .setBackgroundResource(R.drawable.background_influencer)
        }
        getCanals(view = inflate)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    /**
     * Récupération des canaux de discussion
     */
    private fun getCanals(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        viewModel.getAllChannel(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        view.findViewById<TextView>(R.id.pb_chat).visibility = View.GONE
                        view.findViewById<ScrollView>(R.id.chatScrollView).visibility =
                            View.VISIBLE
                        val recyclerChat = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
                        recyclerChat.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        val adapter = ChatAdapter(it.data!!)
                        adapter.notifyDataSetChanged()
                        recyclerChat.adapter = adapter
                    }
                    Status.ERROR -> {
                        view.findViewById<TextView>(R.id.pb_chat).visibility = View.VISIBLE
                        view.findViewById<ScrollView>(R.id.chatScrollView).visibility = View.GONE
                        Log.e("Chat", it.message!!)
                    }
                }
            }
        })
    }
}
