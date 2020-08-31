package c.eip.neoconnect.data.model.message

import c.eip.neoconnect.data.model.ChannelDetail
import com.google.gson.annotations.SerializedName

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