package c.eip.neoconnect.data.model.offres

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Postuler à une offre
 */
class OffresApplied {
    @SerializedName("idUser")
    var idUser: Int = 0

    @SerializedName("idOffer")
    var idOffer: Int = 0

    @SerializedName("status")
    var status: String? = ""
}