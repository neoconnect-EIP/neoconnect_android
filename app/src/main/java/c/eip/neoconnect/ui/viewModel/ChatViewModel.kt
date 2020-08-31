package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.data.repository.ChatRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ChatViewModel : ViewModel() {
    private val chatRepository = ChatRepository()

    fun getAllChannel(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = chatRepository.getAllChannel(token),
                    message = "Récupération des channels réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getOneChannel(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = chatRepository.getOneChannel(token, id),
                    message = "Récupération des messages réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun postMessage(token: String, message: MessageModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = chatRepository.postMessage(token, message),
                    message = "Message envoyé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}