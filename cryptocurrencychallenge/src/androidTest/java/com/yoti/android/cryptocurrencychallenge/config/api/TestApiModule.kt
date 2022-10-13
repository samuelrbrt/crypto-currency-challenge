package com.yoti.android.cryptocurrencychallenge.config.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.NetworkBehavior
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
class TestApiModule {
    @Provides
    @Singleton
    fun serializerProvider(): Gson = GsonBuilder()
        .enableComplexMapKeySerialization()
        .serializeNulls()
        .setLenient()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .setPrettyPrinting()
        .setVersion(1.0)
        .registerTypeAdapter(Date::class.java, DateTypeAdapter())
        .create()

    @Provides
    @Singleton
    fun gsonConverterFactoryProvider(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun behaviorProvider(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(3, TimeUnit.SECONDS)
        behavior.setErrorPercent(30)
        behavior.setVariancePercent(30)
        return behavior
    }

    @Provides
    @Singleton
    fun retrofitProvider(factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://test/")
            .addConverterFactory(factory)
            .build()
    }
}