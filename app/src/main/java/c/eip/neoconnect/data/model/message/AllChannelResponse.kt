package c.eip.neoconnect.data.model.message

import c.eip.neoconnect.data.model.ImagePicture
import com.google.gson.annotations.SerializedName

class AllChannelResponse {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("user_1")
    var user_1: String? = ""
    @SerializedName("user_2")
    var user_2: String? = ""
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("userPicture")
    var userPicture: List<ImagePicture?> = emptyList()
}