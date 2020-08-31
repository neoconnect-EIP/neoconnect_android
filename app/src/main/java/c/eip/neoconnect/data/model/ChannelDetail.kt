package c.eip.neoconnect.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

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