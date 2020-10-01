package c.eip.neoconnect.data.model.message

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Modèle Message Privé
 */
class ChannelDetail {
    @SerializedName("userId")
    var userId: String? = "0"

    @SerializedName("pseudo")
    var pseudo: String? = ""

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("date")
    var date: Date? = null
}