package c.eip.neoconnect.data.model

import com.google.gson.annotations.SerializedName

class ImagePicture {
    @SerializedName("idLink")
    var idLink: String? = "0"
    @SerializedName("imageName")
    var imageName: String? = ""
    @SerializedName("imageData")
    var imageData: String? = ""
}