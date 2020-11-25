package c.eip.neoconnect

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.model.login.LoginResponseModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.data.repository.AuthRepository
import c.eip.neoconnect.data.repository.OffresRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OffresRepositoryTest {
    private val authRepository = AuthRepository()
    private val offresRepository = OffresRepository()
    private val loginInf = LoginModel()
    private val loginShop = LoginModel()
    private var offerId: Int = 0

    @BeforeEach
    fun setup() {
        loginInf.pseudo = "EricChheu"
        loginInf.password = "Azer1234"
        loginShop.pseudo = "testShop1"
        loginShop.password = "Azer1234"
    }

    private fun getTokenInf(loginInf: LoginModel): LoginResponseModel {
        return runBlocking {
            authRepository.login(loginInf)
        }
    }

    private fun getTokenShop(loginShop: LoginModel): LoginResponseModel {
        return runBlocking {
            authRepository.login(loginShop)
        }
    }

    @Test
    fun getAllOffers() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjksInVzZXJUeXBlIjoic2hvcCIsImlhdCI6MTYwNjA2NzYwNSwiZXhwIjoxNjA2MTU0MDA1fQ.vX2On_5vcQEwuqn6sKm5dyOQqinWtkE4sqKeOkQp9Nc"
        val call = runBlocking {
            offresRepository.getAllOffers(
                token = token,
                color = null,
                brand = null,
                order = null,
                popularity = null,
                sex = null,
                subject = null
            )
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getOneOffer() {
        val token = getTokenInf(loginInf).token
        val call = runBlocking {
            offresRepository.getOneOffer(token, 30)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getMyOfferInf() {
        val token = getTokenInf(loginInf).token
        val userId = getTokenInf(loginInf).idUser
        val call = runBlocking {
            offresRepository.getMyOfferInf(token, userId)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun suggestionOffer() {
        val token = getTokenInf(loginInf).token
        val call = runBlocking {
            offresRepository.suggestionOffer(token)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getMyOfferShop() {
        val token = getTokenShop(loginShop).token
        val userId = getTokenShop(loginShop).idUser
        val call = runBlocking {
            offresRepository.getMyOfferShop(token, userId)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun insertOffer() {
        val token = getTokenShop(loginShop).token
        val offre = OffreModel()
        offre.productName = "UT Product"
        offre.productDesc = "Unit Test Product"
        offre.productSex = "Homme"
        offre.productSubject = "Test"
        offre.brand = "Paris & Co"
        offre.color = "Transparent"
        val call = runBlocking {
            offresRepository.insertOffer(token, offre)
        }
        offerId = call.id
        Assertions.assertNotNull(call)
    }

    @Test
    fun editOffer() {
        val token = getTokenShop(loginShop).token
        val offre = OffreModel()
        offre.productName = "UTTT Product"
        offre.productDesc = "Unit Testtt Product"
        offre.productSex = "Femme"
        offre.productSubject = "Testtt"
        offre.brand = "Parisss & Co"
        offre.color = "Transppparent"
        val call = runBlocking {
            offresRepository.editOffer(token, offerId, offre)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun applyOffer() {
        val token = getTokenInf(loginInf).token
        val call = runBlocking {
            offresRepository.applyOffer(token, 11)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun markOffer() {
        val token = getTokenInf(loginInf).token
        val mark = MarkModel()
        mark.mark = 2
        val call = runBlocking {
            offresRepository.markOffer(token, 1, mark)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun commentUser() {
        val token = getTokenInf(loginInf).token
        val comment = CommentModel()
        comment.comment = "Ok"
        val call = runBlocking {
            offresRepository.commentOffer(token, 1, comment)
        }
        Assertions.assertNotNull(call)
    }
}