package c.eip.neoconnect.data.model.report

import com.google.gson.annotations.SerializedName

class OffreReportModel {
    @SerializedName("offreName")
    var offreName: String? = ""

    @SerializedName("subject")
    var subject: String? = ""

    @SerializedName("message")
    var message: String? = ""
}