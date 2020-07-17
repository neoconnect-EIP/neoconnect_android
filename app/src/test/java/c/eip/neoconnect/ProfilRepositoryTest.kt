package c.eip.neoconnect

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.repository.AuthRepository
import c.eip.neoconnect.data.repository.ProfilRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class ProfilRepositoryTest {
    private val profilRepository = ProfilRepository()
    private val authRepository = AuthRepository()
    private val loginInf = LoginModel()
    private val loginShop = LoginModel()

    @BeforeEach
    fun setup() {
        loginInf.pseudo = "testInf1"
        loginInf.password = "Azer1234"
        loginShop.pseudo = "testShop1"
        loginShop.password = "Azer1234"
    }

    private fun getTokenInf(loginInf: LoginModel): String {
        val call = runBlocking {
            authRepository.login(loginInf)
        }
        return call.token
    }

    private fun getTokenShop(loginShop: LoginModel): String {
        val call = runBlocking {
            authRepository.login(loginShop)
        }
        return call.token
    }

    @Test
    fun getProfilInf() {
        val token = getTokenInf(loginInf)
        val call = runBlocking {
            profilRepository.getProfilInf(token)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getListInf() {
        val token = getTokenInf(loginInf)
        val call = runBlocking {
            profilRepository.getListInf(token)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun updateProfilInf() {
        val token = getTokenInf(loginInf)
        val inf = RegisterInfluenceurModel()
        inf.pseudo = loginInf.pseudo
        inf.password = loginInf.password
        inf.email = "android@.test"
        inf.fullName = "kotlin inf"
        inf.sexe = "homme"
        inf.city = "Paris"
        inf.postal = "75700"
        inf.phone = "8888888"
        inf.facebook = "facebooook"
        inf.twitter = "twitttter"
        inf.instagram = "instttagram"
        inf.snapchat = "snapccchat"
        inf.pinterest = "pinttterest"
        inf.twitch = "twitttch"
        inf.youtube = "youtttube"
        inf.theme = "HighTech"
        val call = runBlocking {
            profilRepository.updateProfilInf(token, inf)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getProfilShop() {
        val token = getTokenShop(loginShop)
        val call = runBlocking {
            profilRepository.getProfilShop(token)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getListShop() {
        val token = getTokenShop(loginShop)
        val call = runBlocking {
            profilRepository.getListShop(token)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun updateProfilShop() {
        val token = getTokenShop(loginShop)
        val shop = RegisterShopModel()
        shop.pseudo = loginShop.pseudo
        shop.password = loginShop.password
        shop.email = "android@.test"
        shop.fullName = "kotlin shop"
        shop.city = "Paris"
        shop.postal = "75000"
        shop.phone = "999999999"
        shop.society = "Paris & Co"
        shop.fonction = "PDG"
        shop.website = "google.fr"
        shop.theme = "Cosmétique"
        val call = runBlocking {
            profilRepository.updateProfilShop(token, shop)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun markUser() {
        val token = getTokenInf(loginInf)
        val mark = MarkModel()
        mark.mark = 2
        val call = runBlocking {
            profilRepository.markUser(token, 39, mark)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun commentUser() {
        val token = getTokenInf(loginInf)
        val comment = CommentModel()
        comment.comment = "Ok"
        val call = runBlocking {
            profilRepository.commentUser(token, 39, comment)
        }
        Assertions.assertNotNull(call)
    }
}