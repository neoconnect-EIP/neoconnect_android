package c.eip.neoconnect.data.model.message

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse pour la Récupération d'un canal de discussion
 */
class OneChannelResponse {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("user_1")
    var user_1: String? = ""
    @SerializedName("user_2")
    var user_2: String? = ""
    @SerializedName("data")
    var data: List<ChannelDetail> = emptyList()
}