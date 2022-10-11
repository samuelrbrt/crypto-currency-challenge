package com.yoti.android.cryptocurrencychallenge.asset

import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers
import com.yoti.android.cryptocurrencychallenge.config.state.State

import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val db: AssetDao,
    private val api: AssetApi,
    private val dispatchers: Dispatchers
) {
    fun getAssets() = db.getAssets()

    suspend fun refreshAsset() = withContext(dispatchers.IO) {
        return@withContext try {
            val assets = api.getAssets().assetData
            db.cleansertAssets(assets)
            State.Success(assets)
        } catch (exception: IOException) {
            Timber.e(exception)
            State.Error(exception)
        }
    }
}
