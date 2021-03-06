package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.linksPublication.PublicationLinksModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.*
import c.eip.neoconnect.data.model.report.OffreReportModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
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
        @Query("productSubject") subject: String?,
        @Query("order") order: String?,
        @Query("popularity") popularity: String?
    ): ArrayList<OffreResponseModel>

    /**
     * Récupération de toutes les offres ajoutées par une Marque
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
    ): ApplyModel

    /**
     * Annuler candidature à une offre
     */
    @DELETE("/offer/noapply/{id}")
    suspend fun cancelApplyOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?
    ): String

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
     * Partager une offre avec un autre utilisateur
     */
    @POST("/offer/share/{id}")
    suspend fun shareOffer(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body email: ResetPasswordFirstStepModel
    ): String

    /**
     * Indiquer qu'un produit a été partagé en partageant les liens des posts à la Marque
     */
    @POST("/offer/sharePublication/{id}")
    suspend fun sharePublication(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body links: PublicationLinksModel
    ): String

    /**
     * Suggestions d'offres
     */
    @GET("/offer/suggestion")
    suspend fun suggestionOffer(@Header("authorization") token: String?): ArrayList<OffreSuggestionResponseModel>
}