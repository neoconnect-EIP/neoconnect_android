package c.eip.neoconnect.data.model.contact

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête pour Contacter avec POST /user/feedback
 */
class FeedbackModel {
    @SerializedName("environnement")
    var environnement: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("fonctionnalite")
    var fonctionnalite: String? = null

    @SerializedName("commentaire")
    var commentaire: String? = null

}