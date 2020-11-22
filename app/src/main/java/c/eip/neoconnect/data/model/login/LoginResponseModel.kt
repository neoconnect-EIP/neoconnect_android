package c.eip.neoconnect.data.model.login

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse à la connexion Influenceur ou Marque
 */
class LoginResponseModel {
    @SerializedName("userId")
    var idUser: Int = 0
    @SerializedName("userType")
    var userType: String = ""
    @SerializedName("token")
    var token: String = ""
    @SerializedName("theme")
    var theme: String = ""
}