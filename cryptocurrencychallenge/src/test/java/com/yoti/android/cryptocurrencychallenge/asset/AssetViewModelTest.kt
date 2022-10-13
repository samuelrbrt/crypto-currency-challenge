package com.yoti.android.cryptocurrencychallenge.asset

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetLocalDataSource
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetRemoteDatasource
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetRepository
import com.yoti.android.cryptocurrencychallenge.config.MainCoroutineRule
import com.yoti.android.cryptocurrencychallenge.config.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class AssetViewModelTest {
    private val api = FakeAssetRemoteDatasource()
    private val dao = FakeAssetLocalDataSource()
    private val dispatchers = TestDispatchers()
    private val scope = TestScope(dispatchers.Unconfined)

    private lateinit var vm: AssetViewModel
    private lateinit var repo: AssetRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(dispatchers.Unconfined)

    @Before
    fun setupViewModel() {
        repo = FakeAssetRepository(dao, api, dispatchers)
        vm = AssetViewModel(repo)
    }

    @Test
    fun `test not required`() {
    // Tests are not required as we have tested the remote mediator behavior;
    // No value testing view model when there are no custom logic involved
    }
}