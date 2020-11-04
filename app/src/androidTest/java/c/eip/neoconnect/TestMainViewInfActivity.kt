import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import c.eip.neoconnect.MainViewInf
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.FeedOfferAdapter
import c.eip.neoconnect.ui.adapter.FeedShopAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestMainViewInfActivity {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainViewInf::class.java)

    @Test
    fun testFeedHome() {
        Espresso.onView(withId(R.id.navigation_home)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListOffresPopulaires)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedOfferAdapter.OfferHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListOffresNotes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedOfferAdapter.OfferHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListShopPopulaires)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedShopAdapter.ShopHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerFeedListShopNotes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedShopAdapter.ShopHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
    }

    @Test
    fun testSearch() {
        Espresso.onView(withId(R.id.navigation_search)).perform(click())
        Espresso.onView(withId(R.id.searchKeyword)).perform(typeText("Bibishop"), click())
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerListShop)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedOfferAdapter.OfferHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Espresso.onView(withId(R.id.searchPager)).perform(swipeLeft())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.recyclerListOffer)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FeedOfferAdapter.OfferHolder>(
                0,
                click()
            )
        )
        Espresso.pressBack()
        Espresso.onView(withId(R.id.searchPager)).perform(swipeRight())
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