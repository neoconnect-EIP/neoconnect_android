package c.eip.neoconnect.data.model.profil

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.image.ImagePicture
import c.eip.neoconnect.data.model.mark.MarkModel
import com.google.gson.annotations.SerializedName

/**
 * Modèle Influenceur
 */
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

    @SerializedName("twitterNb")
    var twitterNb: List<Int>? = emptyList()

    @SerializedName("twitterUpdateDate")
    var twitterUpdateDate: List<String>? = emptyList()

    @SerializedName("snapchat")
    var snapchat: String? = ""

    @SerializedName("instagram")
    var instagram: String = ""

    @SerializedName("instagramNb")
    var instagramNb: List<Int>? = emptyList()

    @SerializedName("instagramUpdateDate")
    var instagramUpdateDate: List<String>? = emptyList()

    @SerializedName("pinterest")
    var pinterest: String = ""

    @SerializedName("pinterestNb")
    var pinterestNb: List<Int>? = emptyList()

    @SerializedName("pinterestUpdateDate")
    var pinterestUpdateDate: List<String>? = emptyList()

    @SerializedName("twitch")
    var twitch: String = ""

    @SerializedName("twitchNb")
    var twitchNb: List<Int>? = emptyList()

    @SerializedName("twitchUpdateDate")
    var twitchUpdateDate: List<String>? = emptyList()

    @SerializedName("youtube")
    var youtube: String = ""

    @SerializedName("youtubeNb")
    var youtubeNb: List<Int>? = emptyList()

    @SerializedName("youtubeUpdateDate")
    var youtubeUpdateDate: List<String>? = emptyList()

    @SerializedName("tiktok")
    var tiktok: String = ""

    @SerializedName("tiktokNb")
    var tiktokNb: List<Int>? = emptyList()

    @SerializedName("tiktokUpdateDate")
    var tiktokUpdateDate: List<String>? = emptyList()

    @SerializedName("sexe")
    var sexe: String = ""

    @SerializedName("average")
    var average: String? = ""

    @SerializedName("comment")
    var comment: List<CommentModel> = emptyList()

    @SerializedName("mark")
    var mark: List<MarkModel> = emptyList()

    @SerializedName("visitNumber")
    var visitNumber: Int? = 0

    @SerializedName("countParrainage")
    var countParrainage: Int? = 0

    @SerializedName("codeParrainage")
    var codeParrainage: String? = ""

    @SerializedName("isParraine")
    var isParraine: Boolean? = false

    @SerializedName("nbOfferApplied")
    var nbOfferApplied: Int? = 0
}