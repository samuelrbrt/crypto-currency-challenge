package com.yoti.android.cryptocurrencychallenge.config.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class TestDatabaseModule {
    @Singleton
    @Provides
    fun roomProvider(@ApplicationContext context: Context): Database =
        Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .fallbackToDestructiveMigration()
            .build()

}