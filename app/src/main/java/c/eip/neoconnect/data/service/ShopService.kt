package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import retrofit2.http.*

interface ShopService {
    /**
     * Récupération de son profil Boutique
     */
    @GET("/shop/me")
    suspend fun getShopProfil(
        @Header("authorization") token: String?
    ): ShopResponseModel

    /**
     * Récupération d'un autre profil Boutique
     */
    @GET("/shop/{id}")
    suspend fun getOtherShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): ShopResponseModel

    /**
     * Mise à jour de son compte Boutique
     */
    @PUT("/shop/me")
    suspend fun updateShopProfil(
        @Header("authorization") token: String?,
        @Body shop: RegisterShopModel?
    ): ShopResponseModel

    /**
     * Récupération de la liste des Influenceurs inscrites
     */
    @GET("/shop/listInf")
    suspend fun getListInf(@Header("authorization") token: String?): ArrayList<InfluenceurResponseModel>

    /**
     * Recherche d'une Boutique
     */
    @POST("/shop/search")
    suspend fun searchShop(
        @Header("authorization") token: String?,
        @Body keyword: SearchModel
    ): SearchResponseModel
}