import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.FeedInfAdapter
import c.eip.neoconnect.ui.adapter.FeedOfferAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestMainViewShopActivity {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainViewShop::class.java)

    @Test
    fun testFeedHome() {
        Espresso.onView(withId(R.id.navigation_home)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListInfPopulaires)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedInfAdapter.InfHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListInfNotes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedInfAdapter.InfHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
    }

    @Test
    fun testSearch() {
        Espresso.onView(withId(R.id.navigation_search)).perform(click())
        Espresso.onView(withId(R.id.searchKeyword)).perform(typeText("EricChheu"), click())
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerListInf)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedInfAdapter.InfHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
    }

    @Test
    fun testInsert() {
        Espresso.onView(withId(R.id.navigation_insert_offer)).perform(click())
        Espresso.onView(withId(R.id.insertOfferName)).perform(typeText("Nom"))
        Espresso.onView(withId(R.id.insertOfferDescription)).perform(typeText("Description"))
        Espresso.onView(withId(R.id.insertOfferColor)).perform(typeText("Couleur"))
        Espresso.pressBack()
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
        Espresso.onView(withId(R.id.navigation_my_account_shop)).perform(click())
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