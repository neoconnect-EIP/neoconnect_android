package c.eip.neoconnect.data.model.profil

import c.eip.neoconnect.data.model.ImagePicture
import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import com.google.gson.annotations.SerializedName

class InfluenceurResponseModel {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("pseudo")
    var pseudo: String? = ""

    @SerializedName("userType")
    var userType: String? = "influencer"

    @SerializedName("full_name")
    var fullName: String? = ""

    @SerializedName("email")
    var email: String? = ""

    @SerializedName("phone")
    var phone: String? = ""

    @SerializedName("postal")
    var postal: String? = ""

    @SerializedName("city")
    var city: String? = ""

    @SerializedName("userPicture")
    var userPicture: List<ImagePicture?> = emptyList()

    @SerializedName("userDescription")
    var userDescription: String? = ""

    @SerializedName("theme")
    var theme: String? = ""

    @SerializedName("facebook")
    var facebook: String? = ""

    @SerializedName("twitter")
    var twitter: String? = ""

    @SerializedName("snapchat")
    var snapchat: String? = ""

    @SerializedName("instagram")
    var instagram: String = ""

    @SerializedName("pinterest")
    var pinterest: String = ""

    @SerializedName("twitch")
    var twitch: String = ""

    @SerializedName("youtube")
    var youtube: String = ""

    @SerializedName("tiktok")
    var tiktok: String = ""

    @SerializedName("sexe")
    var sexe: String = ""

    @SerializedName("average")
    var average: String? = ""

    @SerializedName("comment")
    var comment: List<CommentModel> = emptyList()

    @SerializedName("mark")
    var mark: List<MarkModel> = emptyList()
}