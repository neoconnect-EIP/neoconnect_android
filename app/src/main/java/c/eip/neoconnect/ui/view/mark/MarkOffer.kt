package c.eip.neoconnect.ui.view.mark

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.PublicationLinksModel
import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.ui.viewModel.CommentMarkViewModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.CheckInput
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class MarkOffer : Fragment() {
    private lateinit var viewModel: CommentMarkViewModel
    private lateinit var offresViewModel: OffresViewModel
    private var viewState: Int = 0
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
            if (viewState == 0) {
                findNavController().popBackStack()
            } else {
                view.findViewById<LinearLayout>(R.id.markOfferLayout).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.sharePublicationLayout).visibility =
                    View.VISIBLE
                viewState = 0
            }
        }
        view.findViewById<TextView>(R.id.skipSharePublication).setOnClickListener {
            viewState = 1
            view.findViewById<LinearLayout>(R.id.markOfferLayout).visibility = View.VISIBLE
            view.findViewById<LinearLayout>(R.id.sharePublicationLayout).visibility = View.GONE
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

        view.findViewById<ImageView>(R.id.markOfferSendButton).setOnClickListener {
            reviewOffer(view = view, token = token, offerId = offerId)
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
                            viewState = 1
                            view.findViewById<LinearLayout>(R.id.markOfferLayout).visibility =
                                View.VISIBLE
                            view.findViewById<LinearLayout>(R.id.sharePublicationLayout).visibility =
                                View.GONE
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

    /**
     * Commenter et Noter une offre
     */
    private fun reviewOffer(view: View, token: String?, offerId: Int) {
        val markModel = MarkModel()
        val commentModel = CommentModel()
        val mark = view.findViewById<RatingBar>(R.id.markOfferRatingBar).rating
        val comment =
            view.findViewById<TextInputEditText>(R.id.markOfferComment).text.toString()
        viewModel = ViewModelProvider(this).get(CommentMarkViewModel::class.java)
        if (!mark.isNaN()) {
            markModel.mark = mark.toInt()
            viewModel.markOffer(token = token!!, id = offerId, mark = markModel)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, "Note ajouté", Toast.LENGTH_SHORT)
                                    .show()
                                Log.i("Rate offer", it.message!!)
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Echec de l'ajout de la note",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("Rate offer", it.message!!)
                            }
                        }
                    }
                })
        }
        if (comment.isNotEmpty()) {
            commentModel.comment = comment
            viewModel.commentOffer(token = token!!, id = offerId, comment = commentModel)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Log.i("Comment offer", it.message!!)
                                findNavController().popBackStack()
                                Toast.makeText(
                                    context,
                                    "Commentaire ajouté",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Echec de l'ajout du commentaire",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("Comment offer", it.message!!)
                            }
                        }
                    }
                })
        }
    }
}