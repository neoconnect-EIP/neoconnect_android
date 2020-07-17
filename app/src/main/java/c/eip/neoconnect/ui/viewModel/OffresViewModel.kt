package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class OffresViewModel : ViewModel() {
    private val offresRepository = OffresRepository()

    fun getOneOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getOneOffer(token, id),
                    message = "Récupération de l'offre $id réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun insertOffer(token: String, offre: OffreModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.insertOffer(token, offre),
                    message = "Ajout de l'offre réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    //TODO Edit Offer
    fun editOffer(token: String, id: Int, offre: OffreModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.editOffer(token, id, offre),
                    message = "Mise à jour de l'offre réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun applyOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.applyOffer(token, id),
                    message = "Candidature à l'offre $id réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun deleteOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.deleteOffer(token, id),
                    message = "Offre $id supprimé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}