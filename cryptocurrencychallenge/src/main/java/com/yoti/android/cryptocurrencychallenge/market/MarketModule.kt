package com.yoti.android.cryptocurrencychallenge.market

import com.yoti.android.cryptocurrencychallenge.config.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MarketModule {
    @Provides
    @Singleton
    fun apiProvider(retrofit: Retrofit): MarketApi {
        return retrofit.create(MarketApi::class.java)
    }

    @Provides
    @Singleton
    fun dbProvider(db: Database) = db.marketDao()
}