package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.data.repository.UtilsRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ContactViewModel: ViewModel() {
    private val utilsRepository = UtilsRepository()

    fun contactUs(contact: ContactModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.contactUs(contact),
                    message = "Mail envoyé avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun contactUser(contact: ContactUserModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.contactUser(contact),
                    message = "Mail envoyé avec succès à ${contact.dest}"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}