package c.eip.neoconnect.data.model.message

import com.google.gson.annotations.SerializedName

class MessageModel {
    @SerializedName("userId")
    var userId: String? = ""
    @SerializedName("message")
    var message: String? = ""
}