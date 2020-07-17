package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.data.repository.ProfilRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class CommentMarkViewModel: ViewModel() {
    private val profilRepository = ProfilRepository()
    private val offresRepository = OffresRepository()

    //TODO MarkUSer
    fun markUser(token: String, id: Int, mark:MarkModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.markUser(token, id, mark),
                    message = "Note ajouté à l'utilisateur $id"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Comment USer
    fun commentUser(token: String, id: Int, comment:CommentModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.commentUser(token, id, comment),
                    message = "Commentaire ajouté à l'utilisateur $id"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Mark Offer
    fun markOffer(token: String, id: Int, mark:MarkModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.markOffer(token, id, mark),
                    message = "Note ajouté à l'offre $id"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Comment Offer
    fun commentOffer(token: String, id: Int, comment:CommentModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.commentOffer(token, id, comment),
                    message = "Commentaire ajouté à l'offre $id"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}