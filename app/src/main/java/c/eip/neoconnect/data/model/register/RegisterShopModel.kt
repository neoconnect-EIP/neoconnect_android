package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête Inscription Boutique
 */
class RegisterShopModel {
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("password")
    var password: String? = ""
    var passwordCheck: String? = ""

    @SerializedName("full_name")
    var fullName: String? = ""
    @SerializedName("city")
    var city: String? = ""
    @SerializedName("postal")
    var postal: String? = ""
    @SerializedName("phone")
    var phone: String? = ""

    @SerializedName("userPicture")
    var userPicture: String? = ""
    @SerializedName("userDescription")
    var userDescription: String? = ""

    @SerializedName("society")
    var society: String? = ""
    @SerializedName("function")
    var fonction: String? = ""
    @SerializedName("website")
    var website: String? = ""

    @SerializedName("facebook")
    var facebook: String? = ""
    @SerializedName("twitter")
    var twitter: String? = ""
    @SerializedName("instagram")
    var instagram: String = ""
    @SerializedName("snapchat")
    var snapchat: String? = ""
    @SerializedName("theme")
    var theme: String? = ""
}
