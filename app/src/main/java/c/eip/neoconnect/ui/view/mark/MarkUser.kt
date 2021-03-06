package c.eip.neoconnect.ui.view.mark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.ui.viewModel.CommentMarkViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class MarkUser : Fragment() {
    private lateinit var viewModel: CommentMarkViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Modification Fond de vue selon UserType
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_mark_user, container, false)
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.markUserLayout)
                .setBackgroundResource(R.drawable.background_shop)
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.markUserLayout)
                .setBackgroundResource(R.drawable.background_influencer)
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
        view.findViewById<ImageView>(R.id.markUserSendButton).setOnClickListener {
            reviewUser(view = view)
        }
    }

    /**
     * Commenter et Noter un utilisateur
     */
    private fun reviewUser(view: View) {
        val markModel = MarkModel()
        val commentModel = CommentModel()
        val mark = view.findViewById<RatingBar>(R.id.markUserRatingBar).rating
        val comment =
            view.findViewById<TextInputEditText>(R.id.markUserComment).text.toString()
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val userId = arguments?.get("userId") as String
        viewModel = ViewModelProvider(this).get(CommentMarkViewModel::class.java)
        if (!mark.isNaN()) {
            markModel.mark = mark.toInt()
            viewModel.markUser(token = token!!, id = userId.toInt(), mark = markModel)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, "Note ajouté", Toast.LENGTH_SHORT)
                                    .show()
                                Log.i("Rate User", it.message!!)
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Echec de l'ajout de la note",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("Rate User", it.message!!)
                            }
                        }
                    }
                })
        }
        if (comment.isNotEmpty()) {
            commentModel.comment = comment
            viewModel.commentUser(token = token!!, id = userId.toInt(), comment = commentModel)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Log.i("Comment User", it.message!!)
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
                                Log.e("Comment User", it.message!!)
                            }
                        }
                    }
                })
        }
    }
}