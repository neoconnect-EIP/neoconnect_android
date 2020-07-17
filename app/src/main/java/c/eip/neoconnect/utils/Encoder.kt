package c.eip.neoconnect.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.util.*

class Encoder {
    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bytes = baos.toByteArray()
        return Base64.getEncoder().encodeToString(bytes)
    }
}