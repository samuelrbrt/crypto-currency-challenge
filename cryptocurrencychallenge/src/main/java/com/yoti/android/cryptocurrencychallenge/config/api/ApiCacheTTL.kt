package com.yoti.android.cryptocurrencychallenge.config.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ApiCacheTTL(@PrimaryKey val path: String, val lastSyncedAt: Long, val ttlInMillis: Long)