package com.yoti.android.cryptocurrencychallenge.asset

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yoti.android.cryptocurrencychallenge.config.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class AssetViewModel @Inject constructor(repo: AssetRepository) : BaseViewModel() {
    val assets = Pager(PagingConfig(20), remoteMediator = repo.getAssetRemoteMediator())
    { repo.getAssetsPagingSource() }.flow.cachedIn(viewModelScope).asLiveData()
}