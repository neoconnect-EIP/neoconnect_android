package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.ProfilService
import c.eip.neoconnect.utils.Constants

class ProfilRepository {
    var profilService: ProfilService = Constants.profilService

    suspend fun getProfilInf(token: String) = profilService.getInfProfil(token)

    suspend fun getOtherInf(token: String, id: Int) = profilService.getOtherInf(token, id)

    suspend fun getProfilShop(token: String) = profilService.getShopProfil(token)

    suspend fun getOtherShop(token: String, id: Int) = profilService.getOtherShop(token, id)

    suspend fun updateProfilInf(token: String, influenceur: RegisterInfluenceurModel) =
        profilService.updateInfProfil(token, influenceur)

    suspend fun updateProfilShop(token: String, shop: RegisterShopModel) =
        profilService.updateShopProfil(token, shop)

    suspend fun getListInf(token: String) = profilService.getListInf(token)

    suspend fun getListShop(token: String) = profilService.getListShop(token)

    suspend fun markUser(token: String, id: Int, mark: MarkModel) =
        profilService.rateUser(token, id, mark)

    suspend fun commentUser(token: String, id: Int, comment: CommentModel) =
        profilService.commentUser(token, id, comment)

    suspend fun deleteAccount(token: String) = profilService.deleteAccount(token)

    suspend fun searchUser(token: String, keyword: SearchModel) =
        profilService.searchUser(token, keyword)
}