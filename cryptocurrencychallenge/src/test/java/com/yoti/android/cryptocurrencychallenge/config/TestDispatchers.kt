package com.yoti.android.cryptocurrencychallenge.config

import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatchers : Dispatchers {
    private val testScheduler = TestCoroutineScheduler()
    private val unconfined = UnconfinedTestDispatcher(testScheduler)
    private val standard = StandardTestDispatcher(testScheduler)

    override val Main: CoroutineDispatcher = unconfined
    override val IO: CoroutineDispatcher = unconfined
    override val Default: CoroutineDispatcher = standard
    override val Unconfined: CoroutineDispatcher = unconfined
}