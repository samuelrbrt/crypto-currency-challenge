package com.yoti.android.cryptocurrencychallenge.config.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun longToDate(value: Long) = Date(value)

    @TypeConverter
    fun dateToLong(date: Date?) = date?.time ?: 0
}