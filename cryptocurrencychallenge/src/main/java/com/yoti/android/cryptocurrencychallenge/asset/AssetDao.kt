package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AssetDao {
    @Query("SELECT * FROM assetdata ORDER BY rank")
    fun getAssetPagingSource(): PagingSource<Int, Asset>

    @Insert(onConflict = REPLACE)
    fun upsertAssets(assets: List<AssetData>?)

    @Query("DELETE FROM assetdata")
    fun cleanAssets()

    @Transaction
    fun upsertAssetsCleanIfRefresh(assets: List<AssetData>, loadType: LoadType) {
        if (loadType == LoadType.REFRESH) {
            cleanAssets()
        }

        upsertAssets(assets)
    }
}
