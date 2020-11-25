package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.linksPublication.PublicationLinksModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.OffreApply
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.model.report.OffreReportModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.data.service.OffresService
import c.eip.neoconnect.utils.Constants

class OffresRepository {
    private var offresService: OffresService = Constants.offresService

    /**
     * Récupération de toutes les offres
     * ou
     * Récupération de toutes les offres selon un filtre
     */
    suspend fun getAllOffers(
        token: String,
        sex: String?,
        color: String?,
        brand: String?,
        subject: String?,
        order: String?,
        popularity: String?
    ) =
        offresService.getAllOffers(token, sex, color, brand, subject, order, popularity)

    /**
     * Récupération d'une Offre
     */
    suspend fun getOneOffer(token: String, id: Int) = offresService.getOneOffer(token, id)

    /**
     * Récupération de toutes les offres ajoutées par une Marque
     */
    suspend fun getMyOfferShop(token: String, id: Int) = offresService.getMyOfferShop(token, id)

    /**
     * Récupération de toutes les offres postulés par un Influenceur
     */
    suspend fun getMyOfferInf(token: String, id: Int) = offresService.getMyApplyOffers(token, id)

    /**
     * Récupération des utilisateurs ayant postulés à une Offre ID
     */
    suspend fun getOfferApplyUser(token: String, id: Int) =
        offresService.getOfferApplyUser(token, id)

    /**
     * Ajouter une offre
     */
    suspend fun insertOffer(token: String, offre: OffreModel) =
        offresService.insertOffer(token, offre)

    /**
     * Mettre à jour une offre que l'on a posté
     */
    suspend fun editOffer(token: String, id: Int, offre: OffreModel) =
        offresService.editOffer(token, id, offre)

    /**
     * Postuler à une Offre
     */
    suspend fun applyOffer(token: String, id: Int) = offresService.applyOffer(token, id)

    /**
     * Annuler candidature à une Offre
     */
    suspend fun cancelApplyOffer(token: String, id: Int) = offresService.cancelApplyOffer(token, id)

    /**
     * Commenter une Offre
     */
    suspend fun commentOffer(token: String, id: Int, comment: CommentModel) =
        offresService.addCommentOffer(token, id, comment)

    /**
     * Noter une Offre
     */
    suspend fun markOffer(token: String, id: Int, mark: MarkModel) =
        offresService.addMarkOffer(token, id, mark)

    /**
     * Supprimer une offre que l'on a posté
     */
    suspend fun deleteOffer(token: String, id: Int) = offresService.deleteOffer(token, id)

    /**
     * Accepter ou Refuser une candidature d'un Influenceur à une Offre
     */
    suspend fun choiceApply(token: String, choice: OffreApply) =
        offresService.choiceApply(token, choice)

    /**
     * Signaler une offre
     */
    suspend fun reportOffer(token: String, id: Int, report: OffreReportModel) =
        offresService.reportOffer(token, id, report)

    /**
     * Partager une offre avec un autre utilisateur
     */
    suspend fun shareOffer(token: String, id: Int, email: ResetPasswordFirstStepModel) =
        offresService.shareOffer(token, id, email)

    /**
     * Indiquer qu'un produit a été partagé en partageant les liens des posts à la Marque
     */
    suspend fun sharePublication(token: String, id: Int, links: PublicationLinksModel) =
        offresService.sharePublication(token, id, links)

    /**
     * Suggestions d'offres
     */
    suspend fun suggestionOffer(token: String) = offresService.suggestionOffer(token)
}