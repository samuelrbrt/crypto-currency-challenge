package com.yoti.android.cryptocurrencychallenge.asset

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yoti.android.cryptocurrencychallenge.config.MainCoroutineRule
import com.yoti.android.cryptocurrencychallenge.config.TestDispatchers
import com.yoti.android.cryptocurrencychallenge.config.getOrAwaitValue
import com.example.github.config.state.State
import com.yoti.android.cryptocurrencychallenge.asset.FakeTrendingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingViewModelTest {
    private lateinit var vm: TrendingViewModel
    private lateinit var repo: FakeTrendingRepository

    @Test
    fun `empty repo will return empty trending repos`() {
        assertThat(vm.trendingRepositories.getOrAwaitValue(), `is`(emptyList()))
    }

    @Test
    fun `non empty repo will return nonempty trending repos`() {
        val repos = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        repo.repos = repos
        assertThat(vm.trendingRepositories.getOrAwaitValue(), `is`(repos))
    }

    @Test
    fun `force refresh will load data from remote datasource`() {
        val remoteData = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        repo.repos = remoteData

        vm.refreshTrendingRepositories()

        assertThat(vm.repositoryRefreshState.getOrAwaitValue(), `is`(State.Loading()))
        assertThat(vm.repositoryRefreshState.getOrAwaitValue(), `is`(State.Success(remoteData)))
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(TestDispatchers().Unconfined)

    @Before
    fun setupViewModel() {
        repo = FakeTrendingRepository()
        vm = TrendingViewModel(repo)
    }
}