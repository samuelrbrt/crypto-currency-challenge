package com.yoti.android.cryptocurrencychallenge.config.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun roomProvider(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, Database.NAME)
            .fallbackToDestructiveMigration()
            .build()

}