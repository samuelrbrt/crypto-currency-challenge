package com.yoti.android.cryptocurrencychallenge.config.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yoti.android.cryptocurrencychallenge.asset.AssetDao
import com.yoti.android.cryptocurrencychallenge.asset.AssetData


@Database(
    entities = [
        AssetData::class
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
   abstract fun assetDao(): AssetDao

    companion object {
        const val NAME = "app.db"
    }
}