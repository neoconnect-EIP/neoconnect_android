package c.eip.neoconnect.data.model.login

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour se connecter en tant qu'Influenceur ou Marque
 */
data class LoginModel(
    @SerializedName("pseudo")
    var pseudo: String? = "",
    @SerializedName("password")
    var password: String? = ""
)