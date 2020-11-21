package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.parrainage.ParrainageModel
import c.eip.neoconnect.data.model.report.UserReportModel
import c.eip.neoconnect.data.repository.UserRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    /**
     * Supprimer son compte
     */
    fun deleteAccount(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = userRepository.deleteAccount(token = token),
                    message = "Compte supprimé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Signaler un utilisateur
     */
    fun reportUser(token: String, id: Int, report: UserReportModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = userRepository.reportUser(token = token, id = id, report = report),
                    message = "Utilisateur $id (${report.pseudo}) signalé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupéartion des follos
     */
    fun getFollows(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = userRepository.getFollows(token = token),
                    message = "Récupération des abonnements réussi"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Entrer un code parrainage
     */
    fun insertCodeParrainage(code: ParrainageModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = userRepository.insertCodeParrainage(code = code),
                    message = "Code parrainage entré avec succès"
                )
            )
        } catch (e: java.lang.Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}