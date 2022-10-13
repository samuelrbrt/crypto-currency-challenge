package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AssetRemoteMediator @Inject constructor(
    private val api: AssetApi,
    private val assetDao: AssetDao,
    private val dispatchers: Dispatchers
) : RemoteMediator<Int, Asset>() {
    private var offset = 0

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Asset>) = withContext(dispatchers.IO) {
        try {
            offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return@withContext MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull() ?: return@withContext MediatorResult.Success(endOfPaginationReached = true)
                    offset + state.config.pageSize
                }
            }

            val assets = api.getAssets(offset, state.config.pageSize).assetData!!

            assetDao.upsertAssetsCleanIfRefresh(assets, loadType)
            return@withContext MediatorResult.Success(endOfPaginationReached = assets.size < state.config.pageSize)
        } catch (e: Exception) {
            Timber.e(e)
            return@withContext MediatorResult.Error(e)
        }
    }
}