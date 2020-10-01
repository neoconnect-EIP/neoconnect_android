package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.report.UserReportModel
import retrofit2.http.*

interface UserService {
    /**
     * Noter un utilisateur
     */
    @POST("/user/mark/{id}")
    suspend fun rateUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body markForm: MarkModel
    ): MarkModel

    /**
     * Commenter un utilisateur
     */
    @POST("/user/comment/{id}")
    suspend fun commentUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body commentForm: CommentModel
    ): CommentModel

    /**
     * Supprimer son compte
     */
    @DELETE("/user/delete")
    suspend fun deleteAccount(@Header("authorization") token: String?): String

    /**
     * Signaler un utilisateur
     */
    @POST("/user/report/{id}")
    suspend fun reportUser(
        @Header("authorization") token: String?,
        @Path("id") id: Int?,
        @Body report: UserReportModel
    ): String
}