package com.yoti.android.cryptocurrencychallenge.asset

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.google.gson.Gson
import com.yoti.android.cryptocurrencychallenge.config.db.Database
import com.yoti.android.cryptocurrencychallenge.config.init.fake.FakeAssetRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AssetModule::class]
)
class AssetTestModule {
    @Provides
    @Singleton
    fun repositoryProvider(db: AssetDao, remoteMediator: AssetRemoteMediator): AssetRepository {
        return DefaultAssetRepository(db, remoteMediator)
    }
}