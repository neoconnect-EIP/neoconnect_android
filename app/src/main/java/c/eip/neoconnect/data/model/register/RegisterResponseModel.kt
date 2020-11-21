package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse Inscription Influenceur ou Marque
 */
class RegisterResponseModel {
    @SerializedName("token")
    var token: String? = ""
}