package com.yoti.android.cryptocurrencychallenge.config.base

import kotlinx.coroutines.delay

abstract class BaseRepository {
    protected suspend inline fun <reified T: Exception> shouldRetryWith(
        cause: Throwable,
        attempt: Int,
        maxAttempt: Int = 3,
        delay: Long = 3000
    ) = if (cause is T && attempt < maxAttempt) {
        delay(delay)
        true
    } else false
}
