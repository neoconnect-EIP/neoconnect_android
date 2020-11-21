package c.eip.neoconnect.ui.view.mark

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.linksPublication.PublicationLinksModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class MarkOffer : Fragment() {
    private lateinit var offresViewModel: OffresViewModel
    private var check = CheckInput()

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_mark_offer, container, false)
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
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val offerId = arguments?.get("offerId") as Int
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        val links = PublicationLinksModel()
        view.findViewById<TextInputEditText>(R.id.shareFacebookPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val facebookLink =
                        view.findViewById<TextInputEditText>(R.id.shareFacebookPublication)
                    if (!check.checkLinksFacebook(facebookLink.text.toString()) &&
                        facebookLink.text.toString().trim().isNotBlank() &&
                        facebookLink.text.toString().trim().isNotEmpty()
                    ) {
                        facebookLink.error = "Non valide"
                    } else {
                        links.facebook = facebookLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<TextInputEditText>(R.id.shareTwitterPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val twitterLink =
                        view.findViewById<TextInputEditText>(R.id.shareTwitterPublication)
                    if (!check.checkLinksTwitter(twitterLink.text.toString()) &&
                        twitterLink.text.toString().trim().isNotBlank() &&
                        twitterLink.text.toString().trim().isNotEmpty()
                    ) {
                        twitterLink.error = "Non valide"
                    } else {
                        links.twitter = twitterLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        view.findViewById<TextInputEditText>(R.id.shareInstagramPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val instagramLink =
                        view.findViewById<TextInputEditText>(R.id.shareInstagramPublication)
                    if (!check.checkLinksInstagram(instagramLink.text.toString()) &&
                        instagramLink.text.toString().trim().isNotBlank() &&
                        instagramLink.text.toString().trim().isNotEmpty()
                    ) {
                        instagramLink.error = "Non valide"
                    } else {
                        links.instagram = instagramLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<TextInputEditText>(R.id.sharePinterestPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val pinterestLink =
                        view.findViewById<TextInputEditText>(R.id.sharePinterestPublication)
                    if (!check.checkLinksPinterest(pinterestLink.text.toString()) &&
                        pinterestLink.text.toString().trim().isNotBlank() &&
                        pinterestLink.text.toString().trim().isNotEmpty()
                    ) {
                        pinterestLink.error = "Non valide"
                    } else {
                        links.pinterest = pinterestLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<TextInputEditText>(R.id.shareTwitchPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val twitchLink =
                        view.findViewById<TextInputEditText>(R.id.shareTwitchPublication)
                    if (!check.checkLinksTwitch(twitchLink.text.toString()) &&
                        twitchLink.text.toString().trim().isNotBlank() &&
                        twitchLink.text.toString().trim().isNotEmpty()
                    ) {
                        twitchLink.error = "Non valide"
                    } else {
                        links.twitch = twitchLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<TextInputEditText>(R.id.shareYoutubePublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val youtubeLink =
                        view.findViewById<TextInputEditText>(R.id.shareYoutubePublication)
                    if (!check.checkLinksYoutube(youtubeLink.text.toString()) &&
                        youtubeLink.text.toString().trim().isNotBlank() &&
                        youtubeLink.text.toString().trim().isNotEmpty()
                    ) {
                        youtubeLink.error = "Non valide"
                    } else {
                        links.youtube = youtubeLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<TextInputEditText>(R.id.shareTiktokPublication)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val tiktokLink =
                        view.findViewById<TextInputEditText>(R.id.shareTiktokPublication)
                    if (!check.checkLinksTiktok(tiktokLink.text.toString()) &&
                        tiktokLink.text.toString().trim().isNotBlank() &&
                        tiktokLink.text.toString().trim().isNotEmpty()
                    ) {
                        tiktokLink.error = "Non valide"
                    } else {
                        links.youtube = tiktokLink.text.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        view.findViewById<ImageView>(R.id.sharePublicationButton).setOnClickListener {
            sharePub(view = view, token = token, offerId = offerId, links = links)
        }
    }

    /**
     * Partager des publications
     */
    private fun sharePub(view: View, token: String?, offerId: Int, links: PublicationLinksModel) {
        offresViewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
        offresViewModel.sharePublication(token = token!!, id = offerId, links = links)
            .observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                                .show()
                            Log.i("Share Publication", it.message!!)
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                context,
                                "Echec lors de l'envoi des liens",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Share Publication", it.message!!)
                        }
                    }
                }
            })
    }
}