package c.eip.neoconnect

import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

@ExperimentalCoroutinesApi
class AuthRepositoryTest {
    private val authRepository = AuthRepository()
    private val inf = RegisterInfluenceurModel()
    private val loginInf = LoginModel()

    private val shop = RegisterShopModel()
    private val loginShop = LoginModel()

    @BeforeEach
    fun setup() {
        val infData = "InfKotUT" + Random().nextInt(100)
        val shopData = "ShopKotUT" + +Random().nextInt(100)

        inf.pseudo = infData
        inf.password = "Azer1234"

//        loginInf.pseudo = "testInf1"
        loginInf.pseudo = infData
        loginInf.password = "Azer1234"

        shop.pseudo = shopData
        shop.password = "Azer1234"

        loginShop.pseudo = shopData
        loginShop.password = "Azer1234"
    }

    private fun registerInf() {
        inf.email = "android@inf.test"
        inf.fullName = "android inf"
        inf.sexe = "homme"
        inf.city = "Kaunas"
        inf.postal = "43100"
        inf.phone = "0000000000"
        inf.facebook = "facebook"
        inf.twitter = "twitter"
        inf.instagram = "instagram"
        inf.snapchat = "snapchat"
        inf.pinterest = "pinterest"
        inf.twitch = "twitch"
        inf.youtube = "youtube"
        inf.theme = "Jeu Vidéo"
        val call = runBlocking {
            authRepository.registerInfluencer(inf)
        }
        Assertions.assertNotNull(call)
    }

    private fun loginInf() {
        val call = runBlocking {
            authRepository.login(loginInf)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun authInf() {
        registerInf()
        loginInf()
    }

    @Test
    fun registerShop() {
        shop.email = "android1@shop.test"
        shop.fullName = "android shop"
        shop.city = "Kaunas"
        shop.postal = "43110"
        shop.phone = "11111111111"
        shop.website = "google.lt"
        shop.theme = "4"
        shop.userDescription = "Description Shop"
        val call = runBlocking {
            authRepository.registerShop(shop)
        }
        Assertions.assertNotNull(call)
    }

    private fun loginShop() {
        val call = runBlocking {
            authRepository.login(loginShop)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun authShop() {
        registerShop()
        loginShop()
    }
}