package c.eip.neoconnect

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import c.eip.neoconnect.main.MainActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestMainActivityShop {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun atestLandingLoginShop() {
        Espresso.onView(withId(R.id.landingToShop)).perform(click())
        Espresso.onView(withId(R.id.descShopPager)).perform(swipeRight())
        Espresso.onView(withId(R.id.descShopPager)).perform(swipeRight())
        Espresso.onView(withId(R.id.descShopPager)).perform(swipeLeft())
        Espresso.onView(withId(R.id.descShopPager)).perform(swipeLeft())
        Espresso.onView(withId(R.id.descLogin)).perform(click())
        Espresso.onView(withId(R.id.forgotPassword)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.registerButton)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.FAQbutton)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.switchToInfButton)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.loginPseudo)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.loginPassword)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.loginPseudo))
            .perform(replaceText("EricShop"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.loginPassword))
            .perform(replaceText("Azer1234"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.connectButton)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.connectButton)).perform(click())
    }

    @Test
    fun btestFeedHomeShop() {
        Espresso.onView(withId(R.id.navigation_home)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.titleFeed)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.recyclerFeedListInfPopulaires)).check(matches(isDisplayed()))
    }

    @Test
    fun ctestSearchInf() {
        Espresso.onView(withId(R.id.navigation_search)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerListInf)).check(matches(isDisplayed()))
            .perform(swipeUp())
        Espresso.onView(withId(R.id.searchKeyword))
            .perform(replaceText("EricChheu"), pressImeActionButton())
        Thread.sleep(1000)
    }

    @Test
    fun dtestChatShop() {
        Espresso.onView(withId(R.id.navigation_chat)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun etestProfilShop() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.myAccountPicture)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.goToProfil)).perform(click())
        Espresso.onView(withId(R.id.settingsButton)).check(matches(isDisplayed()))
            .perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.myProfilPicture)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilDescription)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilPseudo)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilEmail)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilNom)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilVille)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilPhone)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilPostal)).check(matches(isDisplayed()))
        Espresso.onView(withText(R.string.titleRegister4)).perform(swipeUp(), swipeUp(), swipeUp())
        Espresso.onView(withId(R.id.profilFacebook)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilTwitter)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilInstagram)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilSnapchat)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilWebsite)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.profilSubject)).check(matches(isDisplayed()))
    }

    @Test
    fun ftestOfferShop() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.goToMyOffers)).perform(click())
    }

    @Test
    fun gtestStatsShop() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.goToMyStats)).perform(click())
        Espresso.onView(withId(R.id.statsRatingBar)).check(matches(isDisplayed()))
    }

    @Test
    fun htestContactShop() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.goToContact)).perform(click())
        Espresso.onView(withId(R.id.contactSpinner)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.sendMailSujet)).check(matches(isDisplayed()))
            .perform(replaceText("Test"))
        Espresso.onView(withId(R.id.sendMailMessage)).check(matches(isDisplayed()))
            .perform(replaceText("Message"))
        Espresso.onView(withId(R.id.sendMailButton)).check(matches(isDisplayed()))
    }

    @Test
    fun itestFAQShop() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.goToFAQ)).perform(click())
        Espresso.onView(withId(R.id.question1)).perform(click())
        Espresso.onView(withId(R.id.reponse1)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.question2)).perform(click())
        Espresso.onView(withId(R.id.reponse2)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.question3)).perform(click())
        Espresso.onView(withId(R.id.reponse3)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.question4)).perform(click())
        Espresso.onView(withId(R.id.reponse4)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.question5)).perform(click())
        Espresso.onView(withId(R.id.reponse5)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.question6)).perform(click())
        Espresso.onView(withId(R.id.reponse6)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.moreQuestions)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.backButton)).perform(click())
    }

    @Test
    fun jtestLogout() {
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.logoutButton)).perform(click())
    }
}