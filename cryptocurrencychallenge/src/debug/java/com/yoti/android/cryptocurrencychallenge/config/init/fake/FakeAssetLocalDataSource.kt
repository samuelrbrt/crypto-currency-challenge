package com.yoti.android.cryptocurrencychallenge.config.init.fake

import com.yoti.android.cryptocurrencychallenge.asset.Asset
import com.yoti.android.cryptocurrencychallenge.asset.AssetDao
import com.yoti.android.cryptocurrencychallenge.asset.AssetData

class FakeAssetLocalDataSource(
    var assets: List<AssetData> = emptyList()
) : AssetDao {

    override fun getAssetPagingSource() =
        FakeAssetPagingSource(false, assets.map { with(it) { Asset(id, symbol, name, priceUsd) } })

    override fun upsertAssets(assets: List<AssetData>?) {
        this.assets = assets!!
    }

    override fun cleanAssets() {
        this.assets = emptyList()
    }
}