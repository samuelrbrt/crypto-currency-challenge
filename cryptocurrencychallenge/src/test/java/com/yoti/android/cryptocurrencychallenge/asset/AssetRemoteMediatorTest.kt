package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.*
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetLocalDataSource
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetRemoteDatasource
import com.yoti.android.cryptocurrencychallenge.config.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class AssetRemoteMediatorTest {
    private val api = FakeAssetRemoteDatasource()
    private val dao = FakeAssetLocalDataSource()
    private val dispatchers = TestDispatchers()
    private val scope = TestScope(dispatchers.Unconfined)

    private lateinit var remoteMediator: AssetRemoteMediator

    @Before
    fun init() {
        remoteMediator = AssetRemoteMediator(api, dao, dispatchers)
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = scope.runTest {
        val pagingState = PagingState<Int, Asset>(
            listOf(),
            null,
            PagingConfig(1),
            0
        )
        api.assets = AssetsApiData(listOf(AssetData("1")), null)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result is RemoteMediator.MediatorResult.Success, `is`(true))
        assertThat(
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached,
            `is`(false)
        )
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = scope.runTest {
        val pagingState = PagingState<Int, Asset>(
            listOf(),
            null,
            PagingConfig(1),
            0
        )
        api.assets = AssetsApiData(emptyList(), null)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result is RemoteMediator.MediatorResult.Success, `is`(true))
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached, `is`(true))
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = scope.runTest {
        val pagingState = PagingState<Int, Asset>(
            listOf(),
            null,
            PagingConfig(1),
            0
        )
        api.throwIOException = true
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result is RemoteMediator.MediatorResult.Error, `is`(true))
    }
}