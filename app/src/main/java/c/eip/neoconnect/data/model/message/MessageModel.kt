package c.eip.neoconnect.data.model.message

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Ajouter un message dans un canal de discussion
 */
class MessageModel {
    @SerializedName("userId")
    var userId: String? = ""
    @SerializedName("message")
    var message: String? = ""
}