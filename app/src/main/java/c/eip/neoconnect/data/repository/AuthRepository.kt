package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.service.AuthService
import c.eip.neoconnect.utils.Constants

class AuthRepository {
    var authService: AuthService = Constants.authService

    suspend fun registerInfluencer(RegisterInfluenceurModel: RegisterInfluenceurModel) =
        authService.registerInf(RegisterInfluenceurModel)

    suspend fun registerShop(registerShopModel: RegisterShopModel) =
        authService.registerShop(registerShopModel)

    suspend fun login(loginModel: LoginModel) = authService.login(loginModel)
}