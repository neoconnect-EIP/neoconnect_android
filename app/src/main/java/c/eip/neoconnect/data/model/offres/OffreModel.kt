package c.eip.neoconnect.data.model.offres

import c.eip.neoconnect.data.model.ImagePicture
import com.google.gson.annotations.SerializedName

class OffreModel {
    @SerializedName("productImg")
    var productImg: List<ImagePicture> = emptyList()
    @SerializedName("productName")
    var productName: String? = ""
    @SerializedName("productSex")
    var productSex: String? = ""
    @SerializedName("productDesc")
    var productDesc: String? = ""
    @SerializedName("productSubject")
    var productSubject: String? = ""
    @SerializedName("brand")
    var brand: String? = ""
    @SerializedName("color")
    var color: String? = ""
}