package c.eip.neoconnect.data.model.contact

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Contacter avec POST /user/contact
 */
class ContactUserModel {
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("subject")
    var objet: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("message")
    var message: String? = ""
    @SerializedName("to")
    var dest: String? = ""
}