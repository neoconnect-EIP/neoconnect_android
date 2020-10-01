package c.eip.neoconnect.data.model.resetPassword

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête 1ère étape de la récupération de mot de passe
 */
class ResetPasswordFirstStepModel {
    @SerializedName("email")
    var email: String? = ""
}