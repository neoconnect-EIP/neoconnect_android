package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.repository.UtilsRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class FeedViewModel : ViewModel() {
    private val utilsRepository = UtilsRepository()

    fun getFeed(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.getFeed(token),
                    message = "Actualité récupéré"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = e.message ?: "Une erreur est survenue"
                )
            )
        }
    }
}