package c.eip.neoconnect.data.model.profil

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.image.ImagePicture
import c.eip.neoconnect.data.model.mark.MarkModel
import com.google.gson.annotations.SerializedName

/**
 * Modèle Marque
 */
class ShopResponseModel {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("userType")
    var userType: String? = "shop"
    @SerializedName("full_name")
    var fullName: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("phone")
    var phone: String? = ""
    @SerializedName("city")
    var city: String? = ""
    @SerializedName("postal")
    var postal: String? = ""
    @SerializedName("userDescription")
    var userDescription: String? = ""
    @SerializedName("theme")
    var theme: String? = ""
    @SerializedName("society")
    var society: String? = ""
    @SerializedName("function")
    var fonction: String? = ""
    @SerializedName("website")
    var website: String? = ""
    @SerializedName("facebook")
    var facebook: String? = ""
    @SerializedName("twitter")
    var twitter: String? = ""
    @SerializedName("snapchat")
    var snapchat: String? = ""
    @SerializedName("instagram")
    var instagram: String? = ""
    @SerializedName("userPicture")
    var userPicture: List<ImagePicture?> = emptyList()
    @SerializedName("average")
    var average: String? = ""
    @SerializedName("comment")
    var comment: List<CommentModel> = emptyList()
    @SerializedName("mark")
    var mark: List<MarkModel> = emptyList()
    @SerializedName("nbOfferPosted")
    var nbOfferPosted: Int = 0
}