package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.FeedbackModel
import c.eip.neoconnect.data.repository.UtilsRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ContactViewModel : ViewModel() {
    private val utilsRepository = UtilsRepository()

    /**
     * Envoyer un mail à contact.neoconnect@gmail.com
     */
    fun contactUs(contact: ContactModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.contactUs(contact = contact),
                    message = "Mail envoyé avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Envoyer un message pouvant servir de Retour Utilisateur à contact.neoconnect@gmail.com
     */
    fun sendFeedback(message: FeedbackModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.sendFeedback(message = message),
                    message = "Mail envoyé avec succès"
                )
            )
        } catch (e: java.lang.Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}