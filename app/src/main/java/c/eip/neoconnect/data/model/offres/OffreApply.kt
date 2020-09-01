package c.eip.neoconnect.data.model.offres

import com.google.gson.annotations.SerializedName

class OffreApply {
    @SerializedName("idUser")
    var idUser: Int = 0

    @SerializedName("idOffer")
    var idOffer: Int = 0

    @SerializedName("status")
    var status: Boolean = false
}