package c.eip.neoconnect.utils

import android.content.Context

class DataGetter {
    fun saveToken(context: Context, token: String?) {
        context.getSharedPreferences("user", 0).edit().putString("token", token!!).apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("user", 0).getString("token", "")
    }

    fun saveUserId(context: Context, userId: Int?) {
        context.getSharedPreferences("user", 0).edit().putInt("idUser", userId!!).apply()
    }

    fun getUserId(context: Context): Int {
        return context.getSharedPreferences("user", 0).getInt("idUser", 0)
    }

    fun saveUserType(context: Context, userType: String?) {
        context.getSharedPreferences("user", 0).edit().putString("userType", userType).apply()
    }

    fun getUserType(context: Context): String? {
        return context.getSharedPreferences("user", 0).getString("userType", "")
    }

    fun clearData(context: Context) {
        return context.getSharedPreferences("user", 0).edit().clear().apply()
    }

    companion object {
        val INSTANCE = DataGetter()
    }
}