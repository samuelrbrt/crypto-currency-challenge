package com.yoti.android.cryptocurrencychallenge.market

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {
    @Insert(onConflict = REPLACE)
    suspend fun upsert(market: Market?)

    @Query("SELECT * FROM market WHERE baseId = :baseId")
    fun getMarket(baseId: String): Flow<Market>
}
