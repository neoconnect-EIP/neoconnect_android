package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête Inscription Marque
 */
class CheckFieldModel {
    @SerializedName("pseudo")
    var pseudo: String? = null
    @SerializedName("email")
    var email: String? = null

    @SerializedName("facebook")
    var facebook: String? = null
    @SerializedName("twitter")
    var twitter: String? = null
    @SerializedName("instagram")
    var instagram: String? = null
    @SerializedName("snapchat")
    var snapchat: String? = null
    @SerializedName("pinterest")
    var pinterest: String? = null
    @SerializedName("twitch")
    var twitch: String? = null
    @SerializedName("youtube")
    var youtube: String? = null
    @SerializedName("tiktok")
    var tiktok: String? = null
}
