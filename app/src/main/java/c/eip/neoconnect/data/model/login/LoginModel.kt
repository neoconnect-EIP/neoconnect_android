package c.eip.neoconnect.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("pseudo")
    var pseudo: String? = "",
    @SerializedName("password")
    var password: String? = ""
)