package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.repository.InfRepository
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.data.repository.ShopRepository
import c.eip.neoconnect.data.repository.UserRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ListViewModel : ViewModel() {
    private val offresRepository = OffresRepository()
    private val infRepository = InfRepository()
    private val shopRepository = ShopRepository()
    private val userRepository = UserRepository()

    /**
     * Récupération de la liste des Influenceusrs inscrits
     */
    fun getListInf(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = shopRepository.getListInf(token = token),
                    message = "Récupération des influenceurs réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération de la liste des Marques inscrites
     */
    fun getListShop(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = infRepository.getListShop(token = token),
                    message = "Récupération des marques réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération de toutes les offres
     * ou
     * Récupération de toutes les offres selon un filtre
     */
    fun getOffers(
        token: String,
        sex: String?,
        color: String?,
        brand: String?,
        subject: String?,
        order: String?,
        popularity: String?
    ) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = offresRepository.getAllOffers(
                            token = token,
                            sex = sex,
                            color = color,
                            brand = brand,
                            subject = subject,
                            popularity = popularity,
                            order = order
                        ),
                        message = "Récupération des offres réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    /**
     * Récupération de toutes les offres ajoutées par une Marque
     */
    fun getMyOffersShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getMyOfferShop(token = token, id = id),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération de toutes les offres postulés par un Influenceur
     */
    fun getMyOffersInf(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getMyOfferInf(token = token, id = id),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération des utilisateurs ayant postulés à une Offre ID
     */
    fun getOfferApplyUser(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getOfferApplyUser(token = token, id = id),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération de suggestion d'offres
     */
    fun suggestionOffer(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.suggestionOffer(token = token),
                    message = "Récupération des suggestions d'offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Récupération de suggestion d'offres
     */
    fun suggestionUser(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = userRepository.suggestionUser(token = token),
                    message = "Récupération des suggestions d'offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}