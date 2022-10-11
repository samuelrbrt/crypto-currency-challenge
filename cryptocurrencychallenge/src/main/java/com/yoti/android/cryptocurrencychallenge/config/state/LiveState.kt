package com.yoti.android.cryptocurrencychallenge.config.state

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

open class LiveState<T> : LiveData<State<T>>()

class LiveStateBuilder<T> {
    var onSuccess: (T?) -> Unit = {}
    var onLoading: (Boolean, Int) -> Unit = { _, _ -> }
    var onError: (Throwable) -> Unit = {}
    var onCached: (T?) -> Unit = {}

    fun onSuccess(success: (T?) -> Unit): LiveStateBuilder<T> {
        onSuccess = success
        return this
    }

    fun onLoading(loading: (Boolean, Int) -> Unit): LiveStateBuilder<T> {
        onLoading = loading
        return this
    }

    fun onError(error: (Throwable) -> Unit): LiveStateBuilder<T> {
        onError = error
        return this
    }

    fun onCached(cached: (T?) -> Unit): LiveStateBuilder<T> {
        onCached = cached
        return this
    }
}

fun <T> LiveData<State<T>>.observe(lifecycleOwner: LifecycleOwner): LiveStateBuilder<T> {
    val builder = LiveStateBuilder<T>()

    observe(lifecycleOwner) {
        when (it) {
            is State.Cached -> builder.onCached(it.data)
            is State.Error -> builder.onError(it.exception)
            is State.Init -> {}
            is State.Loading -> builder.onLoading(true, it.progress)
            is State.Success -> builder.onSuccess(it.data)
        }

        if (it !is State.Loading) builder.onLoading(false, 100)
    }

    return builder
}