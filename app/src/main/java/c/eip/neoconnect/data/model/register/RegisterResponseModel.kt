package c.eip.neoconnect.data.model.register

import com.google.gson.annotations.SerializedName

class RegisterResponseModel {
    @SerializedName("token")
    var token: String? = ""
}