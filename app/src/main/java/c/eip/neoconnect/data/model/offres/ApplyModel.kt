package c.eip.neoconnect.data.model.offres

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Postuler à une offre
 */
class ApplyModel {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("idUser")
    var idUser: Int? = 0
    @SerializedName("productImg")
    var productImg: List<String> = emptyList()
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
    @SerializedName("visitNumber")
    var visitNumber: Int? = 0
}