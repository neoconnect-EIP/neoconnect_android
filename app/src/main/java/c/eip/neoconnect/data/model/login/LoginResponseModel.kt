package c.eip.neoconnect.data.model.login

import com.google.gson.annotations.SerializedName

class LoginResponseModel {
    @SerializedName("userId")
    var idUser: Int = 0
    @SerializedName("userType")
    var userType: String = ""
    @SerializedName("token")
    var token: String = ""
}