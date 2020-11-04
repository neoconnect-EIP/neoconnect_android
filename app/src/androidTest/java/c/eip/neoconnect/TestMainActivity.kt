import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import c.eip.neoconnect.MainActivity
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.FeedOfferAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestMainActivity {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMainActivityInf() {
        Espresso.onView(withId(R.id.landingToInf)).perform(click())
        Espresso.onView(withId(R.id.descInfPager)).perform(swipeRight())
        Espresso.onView(withId(R.id.descInfPager)).perform(swipeRight())
        Espresso.onView(withId(R.id.descInfPager)).perform(swipeLeft())
        Espresso.onView(withId(R.id.descInfPager)).perform(swipeLeft())
        Espresso.onView(withId(R.id.descLogin)).perform(click())
        Espresso.onView(withId(R.id.forgotPassword)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.registerButton)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.switchToShopButton)).perform(click())
    }

    @Test
    fun testMainActivityShop() {
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
        Espresso.onView(withId(R.id.switchToInfButton)).perform(click())
    }

    @Test
    fun testChat() {
        Espresso.onView(withId(R.id.navigation_chat)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerViewChat)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedOfferAdapter.OfferHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
    }

    @Test
    fun testMyAccount() {
        Espresso.onView(withId(R.id.navigation_my_account_inf)).perform(click())
        Espresso.onView(withId(R.id.goToProfil)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.goToMyOffers)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.goToContact)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.goToMyStats)).perform(click())
        Espresso.pressBack()
    }
}