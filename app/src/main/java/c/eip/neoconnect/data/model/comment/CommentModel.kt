package c.eip.neoconnect.data.model.comment

import com.google.gson.annotations.SerializedName

class CommentModel {
    @SerializedName("comment")
    var comment: String? = ""

    @SerializedName("pseudo")
    var pseudo: String? = ""
}