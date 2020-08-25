package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.InfService
import c.eip.neoconnect.utils.Constants

class InfRepository {
    private var infService: InfService = Constants.infService

    suspend fun getProfilInf(token: String) = infService.getInfProfil(token)

    suspend fun getOtherInf(token: String, id: Int) = infService.getOtherInf(token, id)

    suspend fun updateProfilInf(token: String, influenceur: RegisterInfluenceurModel) =
        infService.updateInfProfil(token, influenceur)

    suspend fun getListShop(token: String) = infService.getListShop(token)

    suspend fun searchInf(token: String, keyword: SearchModel) =
        infService.searchInf(token, keyword)
}