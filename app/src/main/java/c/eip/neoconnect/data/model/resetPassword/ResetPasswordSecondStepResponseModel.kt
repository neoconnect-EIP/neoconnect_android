package c.eip.neoconnect.data.model.resetPassword

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse 2ème étape de la récupération de mot de passe
 */
class ResetPasswordSecondStepResponseModel {
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("message")
    var message: String? = ""
}