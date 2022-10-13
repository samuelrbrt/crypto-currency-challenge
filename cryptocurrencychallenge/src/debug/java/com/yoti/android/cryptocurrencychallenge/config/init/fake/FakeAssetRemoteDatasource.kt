package com.yoti.android.cryptocurrencychallenge.config.init.fake

import com.yoti.android.cryptocurrencychallenge.asset.AssetApi
import com.yoti.android.cryptocurrencychallenge.asset.AssetsApiData
import java.io.IOException

class FakeAssetRemoteDatasource(var assets: AssetsApiData? = null, var throwIOException: Boolean = false) : AssetApi {
    override suspend fun getAssets(offset: Int, limit: Int): AssetsApiData {
        if (throwIOException) throw IOException("Network error")

        return assets!!
    }
}
