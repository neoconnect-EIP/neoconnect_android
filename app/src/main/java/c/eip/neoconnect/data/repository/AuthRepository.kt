package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.service.AuthService
import c.eip.neoconnect.utils.Constants

class AuthRepository {
    private var authService: AuthService = Constants.authService

    /**
     * Inscription influenceur
     */
    suspend fun registerInfluencer(registerInfluenceurModel: RegisterInfluenceurModel) =
        authService.registerInf(registerInfluenceurModel)

    /**
     * Inscription Marque
     */
    suspend fun registerShop(registerShopModel: RegisterShopModel) =
        authService.registerShop(registerShopModel)

    /**
     * Connexion en Influenceur ou Marque
     */
    suspend fun login(loginModel: LoginModel) = authService.login(loginModel)
}