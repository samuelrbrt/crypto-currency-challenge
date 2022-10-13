package com.yoti.android.cryptocurrencychallenge.config.init.fake

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.yoti.android.cryptocurrencychallenge.asset.*
import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers

@ExperimentalPagingApi
class FakeAssetRepository(
    private val dao: AssetDao,
    private val api: AssetApi,
    private val dispatchers: Dispatchers,
    private val isError: Boolean = false,
    private var assets: List<Asset> = emptyList()
) : AssetRepository {
    override fun getAssetsPagingSource(): PagingSource<Int, Asset> = FakeAssetPagingSource(isError, assets)

    override fun getAssetRemoteMediator(): RemoteMediator<Int, Asset> {
        return AssetRemoteMediator(api, dao, dispatchers)
    }
}