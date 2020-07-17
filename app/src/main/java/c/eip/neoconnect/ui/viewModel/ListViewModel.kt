package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.data.repository.ProfilRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ListViewModel : ViewModel() {
    private val profilRepository = ProfilRepository()
    private val offresRepository = OffresRepository()

    fun getListInf(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getListInf(token),
                    message = "Récupération des influenceurs réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getListShop(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = profilRepository.getListShop(token),
                    message = "Récupération des boutiques réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getOffers(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getAllOffers(token),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getMyOffersShop(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getMyOfferShop(token, id),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun getMyOffersInf(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getMyOfferInf(token, id),
                    message = "Récupération des offres réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}