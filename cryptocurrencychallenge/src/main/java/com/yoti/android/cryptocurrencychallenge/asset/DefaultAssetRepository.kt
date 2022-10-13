package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject

@ExperimentalPagingApi
class DefaultAssetRepository @Inject constructor(
    private val db: AssetDao,
    private val assetRemoteMediator: AssetRemoteMediator
) : AssetRepository {
    override fun getAssetsPagingSource() = db.getAssetPagingSource()

    override fun getAssetRemoteMediator() = assetRemoteMediator
}