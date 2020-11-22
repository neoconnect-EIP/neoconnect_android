package c.eip.neoconnect.data.model.follows

import com.google.gson.annotations.SerializedName

/**
 * Modèle Réponse Follows
 */
class FollowsResponseModel {
    @SerializedName("idUser")
    var idUser: Int? = null
    @SerializedName("idFollow")
    var idFollow: Int? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("pseudo")
    var pseudo: String? = null
    @SerializedName("email")
    var email: String? = null
}