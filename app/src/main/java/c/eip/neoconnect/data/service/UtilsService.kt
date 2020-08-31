package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.data.model.feed.FeedResponseModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordSecondStepResponseModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordThirdStepModel
import retrofit2.http.*

interface UtilsService {
    @POST("/contact")
    suspend fun contactUs(
        @Body contact: ContactModel
    ): String

    @POST("/user/contact")
    suspend fun sendEmailToUser(
        @Body contactUser: ContactUserModel
    ): String

    @POST("/forgotPassword")
    suspend fun forgotPassword(
        @Body email: ResetPasswordFirstStepModel
    ): String

    @GET("/resetPassword/{resetPasswordToken}")
    suspend fun resetPasswordToken(@Path("resetPasswordToken") token: String):
            ResetPasswordSecondStepResponseModel

    @PUT("/updatePassword")
    suspend fun updatePassword(@Body formValue: ResetPasswordThirdStepModel): String

    @GET("/actuality")
    suspend fun getFeed(@Header("authorization") token: String?): FeedResponseModel
}