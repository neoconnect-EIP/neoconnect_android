package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.PublicationLinksModel
import c.eip.neoconnect.data.model.offres.OffreApply
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.model.report.OffreReportModel
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class OffresViewModel : ViewModel() {
    private val offresRepository = OffresRepository()

    /**
     * Récupération d'une Offre
     */
    fun getOneOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.getOneOffer(token = token, id = id),
                    message = "Récupération de l'offre $id réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Ajouter une offre
     */
    fun insertOffer(token: String, offre: OffreModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.insertOffer(token = token, offre = offre),
                    message = "Ajout de l'offre réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Mettre à jour une offre que l'on a posté
     */
    fun editOffer(token: String, id: Int, offre: OffreModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.editOffer(token = token, id = id, offre = offre),
                    message = "Mise à jour de l'offre réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Postuler à une Offre
     */
    fun applyOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.applyOffer(token = token, id = id),
                    message = "Candidature à l'offre $id réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Supprimer une offre que l'on a posté
     */
    fun deleteOffer(token: String, id: Int) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.deleteOffer(token = token, id = id),
                    message = "Offre $id supprimé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Accepter ou Refuser une candidature d'un Influenceur à une Offre
     */
    fun choiceApply(token: String, choice: OffreApply) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.choiceApply(token = token, choice = choice),
                    message = "Influenceur ${choice.idUser} ${
                        if (choice.status) {
                            "accepté"
                        } else {
                            "refusé"
                        }
                    }"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Signaler une offre
     */
    fun reportOffer(token: String, id: Int, report: OffreReportModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = offresRepository.reportOffer(token = token, id = id, report = report),
                    message = "Utilisateur $id (${report.offreName} signalé"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * Indiquer qu'un produit a été partagé en partageant les liens des posts à la Boutique
     */
    fun sharePublication(token: String, id: Int, links: PublicationLinksModel) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = offresRepository.sharePublication(
                            token = token, id = id,
                            links = links
                        ),
                        message = "Lien(s) envoyé avec succès"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }
}