package com.yoti.android.cryptocurrencychallenge.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.config.base.BaseViewModel
import com.yoti.android.cryptocurrencychallenge.config.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class MarketViewModel @Inject constructor(savedState: SavedStateHandle, repo: MarketRepository) : BaseViewModel() {
    private val baseId = savedState.getStateFlow("baseId", "")
    private val retry = MutableSharedFlow<Unit>()

    val marketState: LiveData<State<Market>> = baseId.flatMapLatest {
        repo.refreshMarket(it)
            .map { State.Success(it) as State<Market> }
            .retryWhen { error, _ -> emit(State.Error(error)); retry.first(); emit(State.Loading()); true }
            .catch { error -> emit(State.Error(error)) }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.Loading())
    }.asLiveData()

    val market: LiveData<Market?> = baseId.flatMapLatest { repo.getMarket(it) }.asLiveData()

    fun retry() = viewModelScope.launch {
        retry.emit(Unit)
    }
}