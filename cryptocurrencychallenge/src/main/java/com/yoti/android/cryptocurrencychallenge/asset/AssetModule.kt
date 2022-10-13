package com.yoti.android.cryptocurrencychallenge.asset

import androidx.paging.ExperimentalPagingApi
import com.yoti.android.cryptocurrencychallenge.config.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class AssetModule {
    @Provides
    @Singleton
    fun dbProvider(db: Database) = db.assetDao()

    @Provides
    @Singleton
    fun apiProvider(retrofit: Retrofit) = retrofit.create(AssetApi::class.java)

    @Provides
    @Singleton
    fun repoProvider(db: AssetDao, assetRemoteMediator: AssetRemoteMediator): AssetRepository {
        return DefaultAssetRepository(db, assetRemoteMediator)
    }
}