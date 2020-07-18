package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import retrofit2.http.*

interface OffresService {
    @GET("/offer/list")
    suspend fun getAllOffers(
        @Header("authorization") token: String?,
        @Query("productSex") sex: String?,
        @Query("brand") brand: String?,
        @Query("color") color: String?
    ): ArrayList<OffreResponseModel>

    @GET("/offer/shop/{id}")
    suspend fun getMyOfferShop(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): ArrayList<OffreResponseModel>

    @GET("/offer/apply/user/{id}")
    suspend fun getMyApplyOffers(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): ArrayList<OffreResponseModel>

    @GET("/offer/{id}")
    suspend fun getOneOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): OffreResponseModel

    @PUT("/offer/apply/{id}")
    suspend fun applyOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): OffreResponseModel

    @POST("/offer/comment/{id}")
    suspend fun addCommentOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body comment: CommentModel
    ): CommentModel

    @POST("/offer/mark/{id}")
    suspend fun addMarkOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body mark: MarkModel
    ): MarkModel

    @POST("/offer/insert")
    suspend fun insertOffer(@Header("authorization") token: String?, @Body offer: OffreModel):
            OffreResponseModel

    @PUT("/offer/{id}")
    suspend fun editOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body offer: OffreModel
    ): OffreResponseModel

    @DELETE("/offer/{id}")
    suspend fun deleteOffer(@Header("authorization") token: String?, @Path("id") id: Int?): String

}