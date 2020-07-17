package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.service.OffresService
import c.eip.neoconnect.utils.Constants

class OffresRepository {
    var offresService: OffresService = Constants.offresService

    suspend fun getAllOffers(token: String) = offresService.getAllOffers(token)

    suspend fun getOneOffer(token: String, id: Int) = offresService.getOneOffer(token, id)

    suspend fun getMyOfferShop(token: String, id: Int) = offresService.getMyOfferShop(token, id)

    suspend fun getMyOfferInf(token: String, id: Int) = offresService.getMyApplyOffers(token, id)

    suspend fun insertOffer(token: String, offre: OffreModel) =
        offresService.insertOffer(token, offre)

    suspend fun editOffer(token: String, id: Int, offre: OffreModel) =
        offresService.editOffer(token, id, offre)

    suspend fun applyOffer(token: String, id: Int) = offresService.applyOffer(token, id)

    suspend fun commentOffer(token: String, id: Int, comment: CommentModel) =
        offresService.addCommentOffer(token, id, comment)

    suspend fun markOffer(token: String, id: Int, mark: MarkModel) =
        offresService.addMarkOffer(token, id, mark)

    suspend fun deleteOffer(token: String, id: Int) = offresService.deleteOffer(token, id)
}