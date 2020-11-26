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
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText

class ProfilOtherShop : Fragment() {
    private lateinit var viewModel: ShopViewModel
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var userViewModel: UserViewModel
    private val bundle = bundleOf()
    private var name: String? = ""
    private var buttonState: Int = 0

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_other_shop, container, false)
        when (arguments?.get("mode")) {
            0 -> {
                val id = arguments?.get("id") as Int
                getFollowShop(view = inflate, id = id)
                getOtherProfilShop(inflate = inflate)
            }
            1 -> {
                val id = Search.searchResponse?.id
                getFollowShop(view = inflate, id = id!!)
                resultSearchProfilShop(inflate = inflate)
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
        view.findViewById<TextView>(R.id.getOfferButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_offer_shop, arguments)
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
        view.findViewById<TextView>(R.id.otherShopStats).setOnClickListener {
            findNavController().navigate(
                R.id.navigation_stats,
                bundleOf("profil" to "other", "id" to arguments?.get("id"))
            )
        }
        val token = DataGetter.INSTANCE.getToken(requireContext())
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getFollows(token = token!!).observe(viewLifecycleOwner, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data!!.forEach { element -> follows.add(element.id!!) }
                        Log.i("Get Follows", it.message.toString())
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("Get Follows", it.message.toString())
                    }
                }
            }
        })
        view.findViewById<TextView>(R.id.followUser).setOnClickListener {
            if (buttonState == 0) {
                followShop(view = view)
            } else {
                unfollowShop(view = view)
            }
        }
        view.findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            settingsOption(view = view)
        }
    }


    /**
     * Récupération du profil Marque
     */
    private fun getOtherProfilShop(inflate: View) {
        val id = arguments?.get("id") as Int
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        viewModel.getOtherShop(token = token!!, id = id).observe(viewLifecycleOwner, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        buttonState = if (follows.contains(id)) {
                            inflate.findViewById<TextView>(R.id.followUser)
                                .setText(R.string.unfollow)
                            1
                        } else {
                            inflate.findViewById<TextView>(R.id.followUser).setText(R.string.follow)
                            0
                        }
                        if (it.data?.userPicture?.size!! <= 0) {
                            inflate.findViewById<ImageView>(R.id.otherShopPicture)
                                .setImageResource(R.drawable.ic_picture_shop)
                        } else {
                            Glide.with(requireContext())
                                .load(it.data.userPicture[0]?.imageData)
                                .circleCrop().error(R.drawable.ic_picture_shop)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(inflate.findViewById(R.id.otherShopPicture))
                        }
                        name = it.data.pseudo
                        inflate.findViewById<TextView>(R.id.otherShopPseudo).text =
                            it.data.pseudo
                        inflate.findViewById<TextView>(R.id.otherShopDescription).text =
                            it.data.userDescription
                        val theme = "Thème : " + it.data.theme
                        inflate.findViewById<TextView>(R.id.otherShopSubject).text = theme
                        if (it.data.average.isNullOrBlank()) {
                            inflate.findViewById<TextView>(R.id.otherShopAverage)
                                .setText(R.string.noNotes)
                            inflate.findViewById<TextView>(R.id.otherShopStats).visibility =
                                View.GONE
                        } else {
                            val average = String.format("%.2f", it.data.average!!.toFloat())
                            val note = "Note : $average/5★"
                            inflate.findViewById<TextView>(R.id.otherShopAverage).text = note
                        }
                        val nbOfferPosted = "${it.data.nbOfferPosted}\noffres"
                        inflate.findViewById<TextView>(R.id.otherShopNbOffers).text = nbOfferPosted
                        bundle.putString("dest", it.data.email)

                    }
                    Status.ERROR -> {
                        Log.e("Get Shop", "$id introuvable")
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
     * Récupération du profil Marque par recherche
     */
    private fun resultSearchProfilShop(inflate: View) {
        buttonState = if (follows.contains(Search.searchResponse?.id)) {
            inflate.findViewById<TextView>(R.id.followUser).setText(R.string.unfollow)
            1
        } else {
            inflate.findViewById<TextView>(R.id.followUser).setText(R.string.follow)
            0
        }
        if (Search.searchResponse?.userPicture?.size!! <= 0) {
            inflate.findViewById<ImageView>(R.id.otherShopPicture)
                .setImageResource(R.drawable.ic_picture_shop)
        } else {
            Glide.with(requireContext())
                .load(Search.searchResponse?.userPicture?.get(0)?.imageData)
                .circleCrop().error(R.drawable.ic_picture_shop)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(inflate.findViewById(R.id.otherShopPicture))
        }
        name = Search.searchResponse?.pseudo
        inflate.findViewById<TextView>(R.id.otherShopPseudo).text =
            Search.searchResponse?.pseudo
        if (Search.searchResponse?.average == null) {
            inflate.findViewById<TextView>(R.id.otherShopAverage).setText(R.string.noNotes)
            inflate.findViewById<TextView>(R.id.otherShopStats).visibility =
                View.GONE
        } else {
            val average = String.format("%.2f", Search.searchResponse?.average!!.toFloat())
            val note = "Note : $average/5★"
            inflate.findViewById<TextView>(R.id.otherShopAverage).text = note
        }
        val theme = "Thème : ${Search.searchResponse?.theme}"
        Log.i("Theme", Search.searchResponse?.theme.toString())
        inflate.findViewById<TextView>(R.id.otherShopSubject).text = theme
        inflate.findViewById<TextView>(R.id.otherShopDescription).text =
            Search.searchResponse?.userDescription
        val nbOfferPosted = "${Search.searchResponse?.nbOfferPosted}\noffres"
        inflate.findViewById<TextView>(R.id.otherShopNbOffers).text = nbOfferPosted
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
     * Récupération des followers d'une marque
     */
    private fun getFollowShop(view: View, id: Int) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        viewModel.getFollowShop(token = token!!, id = id).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.i("Get Follow Shop", it.message.toString())
                        val str = "${it.data?.size.toString()}\n abonnés"
                        view.findViewById<TextView>(R.id.otherShopNbFollowers).text = str
                    }
                    Status.ERROR -> {
                        Log.e("Get Follow Shop", it.message.toString())
                    }
                }
            }
        })
    }

    /**
     * Suivre une marque
     */
    private fun followShop(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val id = arguments?.get("id") as Int
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        viewModel.followShop(token = token!!, id = id)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Follow Shop", it.message.toString())
                            buttonState = 1
                            view.findViewById<TextView>(R.id.followUser).setText(R.string.unfollow)
                            getFollowShop(view = view, id = id)
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Follow Shop", it.message.toString())
                        }
                    }
                }
            })
    }

    /**
     * Ne plus suivre une marque
     */
    private fun unfollowShop(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val id = arguments?.get("id") as Int
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        viewModel.unfollowShop(token = token!!, id = id)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Unfollow Shop", it.message.toString())
                            buttonState = 0
                            view.findViewById<TextView>(R.id.followUser).setText(R.string.follow)
                            getFollowShop(view = view, id = id)
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.i("Unfollow Shop", it.message.toString())
                        }
                    }
                }
            })
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

    companion object {
        var follows: ArrayList<Int> = ArrayList()
    }
}
