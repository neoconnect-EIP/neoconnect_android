package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.InfService
import c.eip.neoconnect.utils.Constants

class InfRepository {
    private var infService: InfService = Constants.infService

    /**
     * Récupération de son profil Influenceur
     */
    suspend fun getProfilInf(token: String) = infService.getInfProfil(token)

    /**
     * Récupération d'un autre profil Influenceur
     */
    suspend fun getOtherInf(token: String, id: Int) = infService.getOtherInf(token, id)

    /**
     * Mise à jour de son compte Influenceur
     */
    suspend fun updateProfilInf(token: String, influenceur: RegisterInfluenceurModel) =
        infService.updateInfProfil(token, influenceur)

    /**
     * Récupération de la liste des Marques inscrites
     */
    suspend fun getListShop(token: String) = infService.getListShop(token)

    /**
     * Recherche d'un Influenceur
     */
    suspend fun searchInf(token: String, keyword: SearchModel) =
        infService.searchInf(token, keyword)
}