package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.login.LoginResponseModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterResponseModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    /**
     * Inscription influenceur
     */
    @POST("/inf/register")
    suspend fun registerInf(@Body influenceur: RegisterInfluenceurModel): RegisterResponseModel

    /**
     * Inscription Boutique
     */
    @POST("/shop/register")
    suspend fun registerShop(@Body shop: RegisterShopModel): RegisterResponseModel

    /**
     * Connexion en Influenceur ou Boutique
     */
    @POST("/login")
    suspend fun login(@Body loginModel: LoginModel): LoginResponseModel
}