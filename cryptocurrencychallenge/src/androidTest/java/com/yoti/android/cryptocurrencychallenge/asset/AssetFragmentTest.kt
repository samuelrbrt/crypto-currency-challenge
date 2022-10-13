package com.yoti.android.cryptocurrencychallenge.asset

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.config.ext.atPosition
import com.yoti.android.cryptocurrencychallenge.config.hilt.launchFragmentInHiltContainer
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetLocalDataSource
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetRemoteDatasource
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
@HiltAndroidTest
@MediumTest
class AssetFragmentTest {
    private val fakeApi = FakeAssetRemoteDatasource()
    @BindValue
    val api: AssetApi = fakeApi

    @BindValue
    val db: AssetDao = FakeAssetLocalDataSource()

    @Inject
    lateinit var repo: DefaultAssetRepository
    private lateinit var vm: AssetViewModel

    @Inject
    lateinit var scope: TestScope

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        vm = AssetViewModel(repo)
    }

    @Test
    fun willShowAsset_WhenThereIsAssetInTheDB() {
        db.upsertAssets(listOf(AssetData("1", symbol = "BTC")))
        launchFragmentInHiltContainer<AssetsFragment> {}

        onView(withId(R.id.assetsRV))
            .check(matches(atPosition(0, hasDescendant(withText("BTC")))));
    }

    @Test
    fun willShowErrorState_whenNetworkErrorOccurred() {
        fakeApi.throwIOException = true
        launchFragmentInHiltContainer<AssetsFragment> {}

        onView(withId(R.id.errorTV)).check(matches(isDisplayed()))
    }

    @Test
    fun willNavigateToMarketPage_whenClickedOnAssetItem() {
        db.upsertAssets(listOf(AssetData("1", symbol = "BTC")))

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<AssetsFragment> {
            navController.setGraph(R.navigation.mobile)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.assetsRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AssetsFragment.AssetViewHolder>(0, click()))

        assertThat(navController.currentDestination?.id, `is`(R.id.market))
    }
}