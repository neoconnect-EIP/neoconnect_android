package c.eip.neoconnect.utils

import c.eip.neoconnect.data.service.AuthService
import c.eip.neoconnect.data.service.OffresService
import c.eip.neoconnect.data.service.ProfilService
import c.eip.neoconnect.data.service.UtilsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Constants {
    companion object {
        private const val BASE_URL = "http://168.63.65.106:8080/"
//        private const val BASE_URL = "http://192.168.0.27:8080/"
        private val gson: Gson = GsonBuilder().setLenient().create()
        private var retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()
        val authService: AuthService = retrofit.create(AuthService::class.java)
        val profilService: ProfilService = retrofit.create(ProfilService::class.java)
        val offresService: OffresService = retrofit.create(OffresService::class.java)
        val utilsService: UtilsService = retrofit.create(UtilsService::class.java)
    }
}