package c.eip.neoconnect.data.model.search

import c.eip.neoconnect.data.model.ImagePicture
import com.google.gson.annotations.SerializedName

class SearchResponseModel {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("userType")
    var userType: String = ""

    @SerializedName("pseudo")
    var pseudo: String = ""

    @SerializedName("userPicture")
    var userPicture: List<ImagePicture?> = emptyList()

    @SerializedName("email")
    var email: String = ""

    @SerializedName("theme")
    var theme: String = ""

    @SerializedName("average")
    var average: Int = 0
}