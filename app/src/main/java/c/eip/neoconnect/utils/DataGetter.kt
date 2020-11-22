package c.eip.neoconnect.utils

import android.content.Context

class DataGetter {
    /**
     * Sauvegarde des données dans les préférences de l'application
     * Données sauvegardés : Token, UserId, UserType
     */
    fun saveToken(context: Context, token: String?) {
        context.getSharedPreferences("user", 0).edit().putString("token", token!!).apply()
    }

    fun saveUserId(context: Context, userId: Int?) {
        context.getSharedPreferences("user", 0).edit().putInt("idUser", userId!!).apply()
    }

    fun saveUserType(context: Context, userType: String?) {
        context.getSharedPreferences("user", 0).edit().putString("userType", userType).apply()
    }

    fun saveTheme(context: Context, theme: String?) {
        context.getSharedPreferences("user", 0).edit().putString("theme", theme).apply()
    }

    /**
     * Récupération des données depuis les préférences de l'application
     * Données récupérables : Token, UserId, UserType
     */
    fun getToken(context: Context): String? {
        return context.getSharedPreferences("user", 0).getString("token", "")
    }

    fun getUserId(context: Context): Int {
        return context.getSharedPreferences("user", 0).getInt("idUser", 0)
    }

    fun getUserType(context: Context): String? {
        return context.getSharedPreferences("user", 0).getString("userType", "")
    }

    fun getTheme(context: Context): String? {
        return context.getSharedPreferences("user", 0).getString("theme", "")
    }

    /**
     * Suppression de toutes les donénes dans les préférences de l'application
     */
    fun clearData(context: Context) {
        return context.getSharedPreferences("user", 0).edit().clear().apply()
    }

    companion object {
        val INSTANCE = DataGetter()
    }
}