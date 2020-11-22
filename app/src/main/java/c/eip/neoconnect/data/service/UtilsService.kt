package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.data.model.contact.FeedbackModel
import c.eip.neoconnect.data.model.feed.FeedResponseModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordSecondStepResponseModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordThirdStepModel
import retrofit2.http.*

interface UtilsService {
    /**
     * Envoyer un mail à contact.neoconnect@gmail.com
     */
    @POST("/contact")
    suspend fun contactUs(
        @Body contact: ContactModel
    ): String

    /**
     * Envoyer un mail à un utilisateur
     */
    @POST("/user/contact")
    suspend fun sendEmailToUser(
        @Body contactUser: ContactUserModel
    ): String

    /**
     * 1ère étape de la récupération de mot de passe
     * Signalement de l'oubli de son mot de passe pour recevoir un code par mail
     */
    @POST("/forgotPassword")
    suspend fun forgotPassword(
        @Body email: ResetPasswordFirstStepModel
    ): String

    /**
     * 2ème étape de la récupération de mot de passe
     * Vérification si le code reçu par mail est valide ou non
     */
    @GET("/resetPassword/{resetPasswordToken}")
    suspend fun resetPasswordToken(@Path("resetPasswordToken") token: String):
            ResetPasswordSecondStepResponseModel

    /**
     * 3ème étape de la récupération de mot de passe
     * Modification du mot de passe si Etape 2 validé
     */
    @PUT("/updatePassword")
    suspend fun updatePassword(@Body formValue: ResetPasswordThirdStepModel): String

    /**
     * Récupération du fil d'actualité côté Influenceur et Marque
     */
    @GET("/actuality")
    suspend fun getFeed(@Header("authorization") token: String?): FeedResponseModel

    /**
     * Envoyer un message pouvant servir de Retour Utilisateur à contact.neoconnect@gmail.com
     */
    @POST("/user/feedback")
    suspend fun sendFeedback(@Body message: FeedbackModel): String
}