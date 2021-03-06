package c.eip.neoconnect.utils

import c.eip.neoconnect.data.service.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Constants {
    companion object {
        /**
         *  Adresse IP de l'API
         *
         *  168.63.65.106 = Serveur Prod / Azure
         *  192.168.0.27 = Localhost
         */
        private const val BASE_URL = "http://168.63.65.106:8080/"
//        private const val BASE_URL = "http://146.59.156.45:8080/"
        //        private const val BASE_URL = "http://192.168.0.27:8080/"

        /**
         *  Init de Retrofit et des services
         */
        private val gson: Gson = GsonBuilder().setLenient().create()
        private var retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()
        val authService: AuthService = retrofit.create(AuthService::class.java)
        val userService: UserService = retrofit.create(UserService::class.java)
        val infService: InfService = retrofit.create(InfService::class.java)
        val shopService: ShopService = retrofit.create(ShopService::class.java)
        val offresService: OffresService = retrofit.create(OffresService::class.java)
        val utilsService: UtilsService = retrofit.create(UtilsService::class.java)
        val chatService: ChatService = retrofit.create(ChatService::class.java)
    }
}