package c.eip.neoconnect.data.model.search

import com.google.gson.annotations.SerializedName

/**
 * Modèle Requête Recherche Influenceur ou Marque
 */
class SearchModel {
    @SerializedName("pseudo")
    var pseudo: String = ""
}