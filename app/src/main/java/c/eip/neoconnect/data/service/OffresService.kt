package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.PublicationLinksModel
import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.OffreApply
import c.eip.neoconnect.data.model.offres.OffreApplyUserResponseModel
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.data.model.report.OffreReportModel
import retrofit2.http.*

interface OffresService {
    /**
     * Récupération de toutes les offres
     * ou
     * Récupération de toutes les offres selon un filtre
     */
    @GET("/offer/list")
    suspend fun getAllOffers(
        @Header("authorization") token: String?,
        @Query("productSex") sex: String?,
        @Query("brand") brand: String?,
        @Query("color") color: String?,
        @Query("productSubject") subject: String?
    ): ArrayList<OffreResponseModel>

    /**
     * Récupération de toutes les offres ajoutées par une Boutique
     */
    @GET("/offer/shop/{id}")
    suspend fun getMyOfferShop(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): ArrayList<OffreResponseModel>

    /**
     * Récupération de toutes les offres postulés par un Influenceur
     */
    @GET("/inf/offer/applied/{id}")
    suspend fun getMyApplyOffers(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): ArrayList<OffreResponseModel>

    /**
     * Récupération des utilisateurs ayant postulés à une Offre ID
     */
    @GET("/offer/apply/{id}")
    suspend fun getOfferApplyUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): ArrayList<OffreApplyUserResponseModel>

    /**
     * Récupération d'une Offre
     */
    @GET("/offer/{id}")
    suspend fun getOneOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): OffreResponseModel

    /**
     * Postuler à une Offre
     */
    @PUT("/offer/apply/{id}")
    suspend fun applyOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): OffreResponseModel

    /**
     * Commenter une Offre
     */
    @POST("/offer/comment/{id}")
    suspend fun addCommentOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body comment: CommentModel
    ): CommentModel

    /**
     * Noter une Offre
     */
    @POST("/offer/mark/{id}")
    suspend fun addMarkOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body mark: MarkModel
    ): MarkModel

    /**
     * Ajouter une offre
     */
    @POST("/offer/insert")
    suspend fun insertOffer(@Header("authorization") token: String?, @Body offer: OffreModel):
            OffreResponseModel

    /**
     * Mettre à jour une offre que l'on a posté
     */
    @PUT("/offer/{id}")
    suspend fun editOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body offer: OffreModel
    ): OffreResponseModel

    /**
     * Supprimer une offre que l'on a posté
     */
    @DELETE("/offer/{id}")
    suspend fun deleteOffer(@Header("authorization") token: String?, @Path("id") id: Int?): String

    /**
     * Accepter ou Refuser une candidature d'un Influenceur à une Offre
     */
    @POST("/offer/choiceApply")
    suspend fun choiceApply(
        @Header("authorization") token: String?,
        @Body choice: OffreApply
    ): String

    /**
     * Signaler une offre
     */
    @POST("/offer/report/{id}")
    suspend fun reportOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body report: OffreReportModel
    ): String

    /**
     * Indiquer qu'un produit a été partagé en partageant les liens des posts à la Boutique
     */
    @POST("offer/sharePublication/{id}")
    suspend fun sharePublication(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body links: PublicationLinksModel
    ): String
}