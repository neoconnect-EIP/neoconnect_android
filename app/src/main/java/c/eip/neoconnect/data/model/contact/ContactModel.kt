package c.eip.neoconnect.data.model.contact

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Contacter avec POST /contact
 */
class ContactModel {
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("subject")
    var objet: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("message")
    var message: String? = ""
}