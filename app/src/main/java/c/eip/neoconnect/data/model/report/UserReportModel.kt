package c.eip.neoconnect.data.model.report

import com.google.gson.annotations.SerializedName

class UserReportModel {
    @SerializedName("pseudo")
    var pseudo: String? = ""

    @SerializedName("subject")
    var subject: String? = ""

    @SerializedName("message")
    var message: String? = ""
}