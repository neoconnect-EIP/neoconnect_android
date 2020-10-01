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

    /**
     * Envoyer un mail à contact.neoconnect@gmail.com
     */
    suspend fun contactUs(contact: ContactModel) = utilsService.contactUs(contact)

    /**
     * Envoyer un mail à un utilisateur
     */
    suspend fun contactUser(contact: ContactUserModel) = utilsService.sendEmailToUser(contact)

    /**
     * 1ère étape de la récupération de mot de passe
     * Signalement de l'oubli de son mot de passe pour recevoir un code par mail
     */
    suspend fun forgotPassword(email: ResetPasswordFirstStepModel) = utilsService.forgotPassword(email)

    /**
     * 2ème étape de la récupération de mot de passe
     * Vérification si le code reçu par mail est valide ou non
     */
    suspend fun checkResetPasswordToken(token: String) = utilsService.resetPasswordToken(token)

    /**
     * 3ème étape de la récupération de mot de passe
     * Modification du mot de passe si Etape 2 validé
     */
    suspend fun updatePassword(form: ResetPasswordThirdStepModel) = utilsService.updatePassword(form)

    /**
     * Récupération du fil d'actualité côté Influenceur et Boutique
     */
    suspend fun getFeed(token: String) = utilsService.getFeed(token)

    /**
     * Envoyer un message pouvant servir de Retour Utilisateur à contact.neoconnect@gmail.com
     */
    suspend fun sendFeedback(message: FeedbackModel) = utilsService.sendFeedback(message)
}