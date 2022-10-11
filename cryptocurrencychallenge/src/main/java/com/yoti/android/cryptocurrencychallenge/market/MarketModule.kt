package com.yoti.android.cryptocurrencychallenge.market

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
    fun apiProvider(retrofit: Retrofit): CoinCapService {
        return retrofit.create(CoinCapService::class.java)
    }
}