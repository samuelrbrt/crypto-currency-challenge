package com.yoti.android.cryptocurrencychallenge.asset

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AssetDao {
    @Query("SELECT * FROM assetdata")
    fun getAssets(): LiveData<List<Asset>>

    @Insert(onConflict = REPLACE)
    fun upsertAssets(assets: List<AssetData>?)

    @Query("DELETE FROM assetdata")
    fun cleanAssets()

    @Transaction
    fun cleansertAssets(assets: List<AssetData>?) {
        cleanAssets()
        upsertAssets(assets)
    }
}
