package c.eip.neoconnect.data.model.search

import c.eip.neoconnect.data.model.image.ImagePicture
import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse à la recherche
 */
class SearchResponseModel {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("userType")
    var userType: String = ""

    @SerializedName("pseudo")
    var pseudo: String = ""

    @SerializedName("userDescription")
    var userDescription: String = ""

    @SerializedName("userPicture")
    var userPicture: List<ImagePicture?> = emptyList()

    @SerializedName("email")
    var email: String = ""

    @SerializedName("theme")
    var theme: String? = null

    @SerializedName("average")
    var average: Float? = null

    @SerializedName("nbOfferApplied")
    var nbOfferApplied: Int = 0


    @SerializedName("nbOfferPosted")
    var nbOfferPosted: Int = 0
}