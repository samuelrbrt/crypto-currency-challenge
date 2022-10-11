package com.yoti.android.cryptocurrencychallenge.config.coroutine

import android.content.Context
import com.yoti.android.cryptocurrencychallenge.config.init.CryptoApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {
    @Provides
    @Singleton
    fun coroutineAppScopeProvider(@ApplicationContext context: Context) = (context as CryptoApp).appScope

    @Provides
    @Singleton
    fun coroutineDispatchersProvider(): Dispatchers = CoroutineDispatchers()
}