package c.eip.neoconnect.data.model.resetPassword

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête 3ème étape de la récupération de mot de passe
 */
class ResetPasswordThirdStepModel {
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("resetPasswordToken")
    var token: String? = ""
    @SerializedName("password")
    var password: String? = ""
}