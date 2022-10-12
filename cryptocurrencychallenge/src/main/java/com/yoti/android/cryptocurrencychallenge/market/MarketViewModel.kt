package com.yoti.android.cryptocurrencychallenge.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.yoti.android.cryptocurrencychallenge.config.base.BaseViewModel
import com.yoti.android.cryptocurrencychallenge.config.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MarketViewModel @Inject constructor(savedState: SavedStateHandle, repo: MarketRepository) : BaseViewModel() {
    private val baseId = savedState.getStateFlow("baseId", "")

    val marketState: LiveData<State<Market>> = baseId.flatMapLatest { repo.refreshMarket(it) }.asScopedLiveState()

    val market: LiveData<Market?> = baseId.flatMapLatest { repo.getMarket(it) }.asLiveData()
}