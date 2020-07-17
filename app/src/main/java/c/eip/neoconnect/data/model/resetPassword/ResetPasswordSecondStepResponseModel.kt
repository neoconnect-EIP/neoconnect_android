package c.eip.neoconnect.data.model.resetPassword

import com.google.gson.annotations.SerializedName

class ResetPasswordSecondStepResponseModel {
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("message")
    var message: String? = ""
}