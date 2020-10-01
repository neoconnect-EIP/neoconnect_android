package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.repository.InfRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class InfViewModel : ViewModel() {
    private val infRepository = InfRepository()

    /**
     * Récupération de son profil Influenceur
     */
    fun getProfilInf(token: String) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = infRepository.getProfilInf(token = token),
                        message = "Récupération des données réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    /**
     * Récupération d'un autre profil Influenceur
     */
    fun getOtherInf(token: String, id: Int) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = infRepository.getOtherInf(token = token, id = id),
                        message = "Récupération des données réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    /**
     * Mise à jour de son compte Influenceur
     */
    fun updateProfilInf(token: String, influenceur: RegisterInfluenceurModel) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = infRepository.updateProfilInf(
                            token = token,
                            influenceur = influenceur
                        ),
                        message = "Mise à jour réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    /**
     * Recherche d'un Influenceur
     */
    fun searchInf(token: String, keyword: SearchModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = infRepository.searchInf(token = token, keyword = keyword),
                    message = "Utilisateur trouvé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}