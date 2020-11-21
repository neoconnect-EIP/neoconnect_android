package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.repository.ShopRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ShopViewModel : ViewModel() {
    private val shopRepository = ShopRepository()

    /**
     * Récupération de son profil Marque
     */
    fun getProfilShop(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.getProfilShop(token = token),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération d'un autre profil Marque
     */
    fun getOtherShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.getOtherShop(token = token, id = id),
                    message = "Récupération des données réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Mise à jour de son compte Marque
     */
    fun updateProfilShop(token: String, shop: RegisterShopModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.updateProfilShop(token = token, shop = shop),
                    message = "Mise à jour réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Recherche d'une Marque
     */
    fun searchShop(token: String, keyword: SearchModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.searchShop(token = token, keyword = keyword),
                    message = "Utilisateur trouvé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Voir abonnées d'une marque
     */
    fun getFollowShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.getFollowShop(token = token, id = id),
                    message = "Récupération des abonnements réussi"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Suivre une marque
     */
    fun followShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.followShop(token = token, id = id),
                    message = "Abonnement réussi"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Ne plus suivre une marque
     */
    fun unfollowShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.unfollowShop(token = token, id = id),
                    message = "Désabonnement réussi"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}