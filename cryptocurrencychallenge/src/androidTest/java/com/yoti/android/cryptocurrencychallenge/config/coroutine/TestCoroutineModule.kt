package com.yoti.android.cryptocurrencychallenge.config.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineModule::class]
)
class TestCoroutineModule {
    @Provides
    @Singleton
    fun coroutineDispatchersProvider(): Dispatchers = TestDispatchers()

    @Provides
    @Singleton
    fun testCoroutineScopeProvider(dispatchers: Dispatchers): TestScope = TestScope(dispatchers.Unconfined)
}