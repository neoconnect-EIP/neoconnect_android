package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.data.model.contact.FeedbackModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordThirdStepModel
import c.eip.neoconnect.data.service.UtilsService
import c.eip.neoconnect.utils.Constants

class UtilsRepository {
    private var utilsService: UtilsService = Constants.utilsService

    suspend fun contactUs(contact: ContactModel) = utilsService.contactUs(contact)

    suspend fun contactUser(contact: ContactUserModel) = utilsService.sendEmailToUser(contact)

    suspend fun forgotPassword(email: ResetPasswordFirstStepModel) = utilsService.forgotPassword(email)

    suspend fun checkResetPasswordToken(token: String) = utilsService.resetPasswordToken(token)

    suspend fun updatePassword(form: ResetPasswordThirdStepModel) = utilsService.updatePassword(form)

    suspend fun getFeed(token: String) = utilsService.getFeed(token)

    suspend fun sendFeedback(message: FeedbackModel) = utilsService.sendFeedback(message)
}