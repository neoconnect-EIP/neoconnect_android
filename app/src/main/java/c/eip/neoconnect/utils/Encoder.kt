package c.eip.neoconnect.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.util.*

class Encoder {
    /**
     * Encode une image au format Base64
     */
    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 0, baos)
        val bytes = baos.toByteArray()
        return Base64.getEncoder().encodeToString(bytes)
    }
}