package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse Inscription Influenceur ou Boutique
 */
class RegisterResponseModel {
    @SerializedName("token")
    var token: String? = ""
}