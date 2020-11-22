package c.eip.neoconnect.data.model.offres

import c.eip.neoconnect.data.model.image.ImagePicture
import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse pour la Récupération de tout les Influenceurs ayant postulés à une offre
 */
class OffreApplyUserResponseModel {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("idUser")
    var idUser: Int = 0
    @SerializedName("idOffer")
    var idOffer: Int = 0
    @SerializedName("average")
    var average: String? = ""
    @SerializedName("theme")
    var theme: String? = ""
    @SerializedName("pseudo")
    var pseudo: String? = ""
    @SerializedName("userPicture")
    var userPicture: List<ImagePicture> = emptyList()
    @SerializedName("status")
    var status: String? = ""
}