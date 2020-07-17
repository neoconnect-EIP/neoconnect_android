package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.repository.ProfilRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProfilViewModel : ViewModel() {
    private val profilRepository = ProfilRepository()

    fun getProfilInf(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getProfilInf(token),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getOtherInf(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getOtherInf(token, id),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Update Profil Inf
    fun updateProfilInf(token: String, influenceur: RegisterInfluenceurModel) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = profilRepository.updateProfilInf(token, influenceur),
                        message = "Mise à jour réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    fun getProfilShop(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getProfilShop(token),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getOtherShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getOtherShop(token, id),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Update Profil Shop
    fun updateProfilShop(token: String, shop: RegisterShopModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.updateProfilShop(token, shop),
                    message = "Mise à jour réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun deleteAccount(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.deleteAccount(token),
                    message = "Compte supprimé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun searchUser(token: String, keyword: SearchModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.searchUser(token, keyword),
                    message = "Utilisateur trouvé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}