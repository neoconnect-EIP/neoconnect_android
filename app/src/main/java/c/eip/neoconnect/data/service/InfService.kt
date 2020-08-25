package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import retrofit2.http.*

interface InfService {
    @GET("/inf/me")
    suspend fun getInfProfil(
        @Header("authorization") token: String?
    ): InfluenceurResponseModel

    @GET("/inf/{id}")
    suspend fun getOtherInf(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): InfluenceurResponseModel

    @PUT("/inf/me")
    suspend fun updateInfProfil(
        @Header("authorization") token: String?,
        @Body influenceur: RegisterInfluenceurModel?
    ): InfluenceurResponseModel

    @GET("/inf/listShop")
    suspend fun getListShop(@Header("authorization") token: String?): ArrayList<ShopResponseModel>

    @POST("/inf/search")
    suspend fun searchInf(
        @Header("authorization") token: String?,
        @Body keyword: SearchModel
    ): SearchResponseModel
}