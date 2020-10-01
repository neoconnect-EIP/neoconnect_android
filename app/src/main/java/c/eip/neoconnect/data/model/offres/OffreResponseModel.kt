package c.eip.neoconnect.data.model.offres

import c.eip.neoconnect.data.model.ImagePicture
import c.eip.neoconnect.data.model.comment.CommentModel
import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse pour la Récupération d'une offre
 */
class OffreResponseModel {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("idUser")
    var idUser: Int = 0
    @SerializedName("idOffer")
    var idOffer: Int = 0
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
    @SerializedName("average")
    var average: String? = ""
    @SerializedName("comment")
    var comment: List<CommentModel> = emptyList()
    @SerializedName("status")
    var status: String? = null
}