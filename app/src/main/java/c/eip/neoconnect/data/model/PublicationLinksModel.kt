package c.eip.neoconnect.data.model

import com.google.gson.annotations.SerializedName

class PublicationLinksModel {
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
}