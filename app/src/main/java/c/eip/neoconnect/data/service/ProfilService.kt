package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import retrofit2.http.*

interface ProfilService {
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

    @GET("/shop/me")
    suspend fun getShopProfil(
        @Header("authorization") token: String?
    ): ShopResponseModel

    @GET("/shop/{id}")
    suspend fun getOtherShop(
        @Header("authorization") token: String?, @Path("id") id: Int?
    ): ShopResponseModel

    @PUT("/shop/me")
    suspend fun updateShopProfil(
        @Header("authorization") token: String?,
        @Body shop: RegisterShopModel?
    ): ShopResponseModel

    @GET("/shop/listInf")
    suspend fun getListInf(@Header("authorization") token: String?): ArrayList<InfluenceurResponseModel>

    @GET("/inf/listShop")
    suspend fun getListShop(@Header("authorization") token: String?): ArrayList<ShopResponseModel>

    @POST("/user/mark/{id}")
    suspend fun rateUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body markForm: MarkModel
    ): MarkModel

    @POST("/user/comment/{id}")
    suspend fun commentUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body commentForm: CommentModel
    ): CommentModel

    @DELETE("/user/delete")
    suspend fun deleteAccount(@Header("authorization") token: String?): String

    @POST("/user/search")
    suspend fun searchUser(
        @Header("authorization") token: String?,
        @Body keyword: SearchModel
    ): SearchResponseModel
}