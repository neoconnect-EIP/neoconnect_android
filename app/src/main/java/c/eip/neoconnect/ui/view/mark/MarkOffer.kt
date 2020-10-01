package c.eip.neoconnect.ui.view.mark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class MarkOffer : Fragment() {
    private lateinit var viewModel: CommentMarkViewModel
    private lateinit var offresViewModel: OffresViewModel
    private var viewState: Int = 0

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mark_offer, container, false)
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

        view.findViewById<ImageView>(R.id.sharePublicationButton).setOnClickListener {
            val links = PublicationLinksModel()
            if (view.findViewById<TextInputEditText>(R.id.shareFacebookPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareFacebookPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.facebook =
                    view.findViewById<TextInputEditText>(R.id.shareFacebookPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareTwitterPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareTwitterPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.twitter =
                    view.findViewById<TextInputEditText>(R.id.shareTwitterPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareSnapchatPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareSnapchatPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.snapchat =
                    view.findViewById<TextInputEditText>(R.id.shareSnapchatPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareInstagramPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareInstagramPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.instagram =
                    view.findViewById<TextInputEditText>(R.id.shareInstagramPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.sharePinterestPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.sharePinterestPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.pinterest =
                    view.findViewById<TextInputEditText>(R.id.sharePinterestPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareTwitchPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareTwitchPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.twitch =
                    view.findViewById<TextInputEditText>(R.id.shareTwitchPublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareYoutubePublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareYoutubePublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.youtube =
                    view.findViewById<TextInputEditText>(R.id.shareYoutubePublication).text.toString()
            }
            if (view.findViewById<TextInputEditText>(R.id.shareTiktokPublication).text.toString()
                    .trim().isNotBlank() &&
                view.findViewById<TextInputEditText>(R.id.shareTiktokPublication).text.toString()
                    .trim().isNotEmpty()
            ) {
                links.tiktok =
                    view.findViewById<TextInputEditText>(R.id.shareTiktokPublication).text.toString()
            }
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