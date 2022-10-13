package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
interface AssetRepository {
    fun getAssetsPagingSource(): PagingSource<Int, Asset>

    fun getAssetRemoteMediator(): RemoteMediator<Int, Asset>
}
