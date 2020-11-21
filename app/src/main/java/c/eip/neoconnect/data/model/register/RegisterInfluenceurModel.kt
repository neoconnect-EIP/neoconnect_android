package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête Inscription Influenceur
 */
class RegisterInfluenceurModel {
    @SerializedName("pseudo")
    var pseudo: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("password")
    var password: String? = null

    @SerializedName("full_name")
    var fullName: String? = null
    @SerializedName("sexe")
    var sexe: String? = null
    @SerializedName("city")
    var city: String? = null
    @SerializedName("postal")
    var postal: String? = null
    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("userPicture")
    var userPicture: String? = null
    @SerializedName("userDescription")
    var userDescription: String? = null

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
    @SerializedName("theme")
    var theme: String? = null
}