package c.eip.neoconnect.data.model.resetPassword

import com.google.gson.annotations.SerializedName

class ResetPasswordThirdStepModel {
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("resetPasswordToken")
    var token: String? = ""
    @SerializedName("password")
    var password: String? = ""
}