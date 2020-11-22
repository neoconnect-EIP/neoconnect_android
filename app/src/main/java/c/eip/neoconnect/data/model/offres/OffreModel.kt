package c.eip.neoconnect.data.model.offres

import c.eip.neoconnect.data.model.image.ImagePicture
import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour l'Ajout d'une offre
 */
class OffreModel {
    @SerializedName("productImg")
    var productImg: List<ImagePicture> = emptyList()

    @SerializedName("productName")
    var productName: String? = null

    @SerializedName("productSex")
    var productSex: String? = null

    @SerializedName("productDesc")
    var productDesc: String? = null

    @SerializedName("productSubject")
    var productSubject: String? = null

    @SerializedName("brand")
    var brand: String? = null

    @SerializedName("color")
    var color: String? = null
}