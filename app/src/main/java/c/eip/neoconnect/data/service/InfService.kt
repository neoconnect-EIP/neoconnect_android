package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import retrofit2.http.*

interface InfService {
    /**
     * Récupération de son profil Influenceur
     */
    @GET("/inf/me")
    suspend fun getInfProfil(
        @Header("authorization") token: String?
    ): InfluenceurResponseModel

    /**
     * Récupération d'un autre profil Influenceur
     */
    @GET("/inf/{id}")
    suspend fun getOtherInf(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): InfluenceurResponseModel

    /**
     * Mise à jour de son compte Influenceur
     */
    @PUT("/inf/me")
    suspend fun updateInfProfil(
        @Header("authorization") token: String?,
        @Body influenceur: RegisterInfluenceurModel?
    ): InfluenceurResponseModel

    /**
     * Récupération de la liste des Boutiques inscrites
     */
    @GET("/inf/listShop")
    suspend fun getListShop(@Header("authorization") token: String?): ArrayList<ShopResponseModel>

    /**
     * Recherche d'un Influenceur
     */
    @POST("/inf/search")
    suspend fun searchInf(
        @Header("authorization") token: String?,
        @Body keyword: SearchModel
    ): SearchResponseModel
}