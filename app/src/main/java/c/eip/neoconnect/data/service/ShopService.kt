package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.follows.FollowsResponseModel
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import retrofit2.http.*

interface ShopService {
    /**
     * Récupération de son profil Marque
     */
    @GET("/shop/me")
    suspend fun getShopProfil(
        @Header("authorization") token: String?
    ): ShopResponseModel

    /**
     * Récupération d'un autre profil Marque
     */
    @GET("/shop/{id}")
    suspend fun getOtherShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): ShopResponseModel

    /**
     * Mise à jour de son compte Marque
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
     * Recherche d'une Marque
     */
    @POST("/shop/search")
    suspend fun searchShop(
        @Header("authorization") token: String?,
        @Body keyword: SearchModel
    ): SearchResponseModel

    /**
     * Voir abonnées d'une marque
     */
    @GET("/shop/follow/{id}")
    suspend fun getFollowShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): ArrayList<FollowsResponseModel>

    /**
     * Suivre une marque
     */
    @PUT("/shop/follow/{id}")
    suspend fun followShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): String


    /**
     * Ne plus suivre une marque
     */
    @PUT("/shop/unfollow/{id}")
    suspend fun unfollowShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): String
}