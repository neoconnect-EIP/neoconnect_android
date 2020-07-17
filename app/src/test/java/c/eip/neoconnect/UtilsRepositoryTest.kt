package c.eip.neoconnect

import c.eip.neoconnect.data.model.contact.ContactModel
import c.eip.neoconnect.data.model.contact.ContactUserModel
import c.eip.neoconnect.data.repository.UtilsRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilsRepositoryTest {
    private val utilsRepository = UtilsRepository()

    @Test
    fun contact() {
        val contact = ContactModel()
        contact.pseudo = "Unit Test Kotlin"
        contact.email = "fromUnitTest@test.com"
        contact.objet = "Test"
        contact.message = "Test From Kotlin Unit Test"
        val call = runBlocking {
            utilsRepository.contactUs(contact)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun contactUser() {
        val contact = ContactUserModel()
        contact.pseudo = "Unit Test Kotlin"
        contact.email = "fromUnitTest@test.com"
        contact.objet = "Test"
        contact.message = "Test From Kotlin Unit Test"
        contact.dest = "eric.chheu@epitech.eu"
        val call = runBlocking {
            utilsRepository.contactUser(contact)
        }
        Assertions.assertNotNull(call)
    }

    @Test
    fun getFeed() {
        val call = runBlocking {
            utilsRepository.getFeed(token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjYsInVzZXJUeXBlIjoic2hvcCIsImlhdCI6MTU5NDYzOTkyNSwiZXhwIjoxNTk0NzI2MzI1fQ.8HTRxBtqOr0wD7zFZzjVUX8Johhd8lpNABg2JebtvRE")
        }
        Assertions.assertNotNull(call)
    }
}