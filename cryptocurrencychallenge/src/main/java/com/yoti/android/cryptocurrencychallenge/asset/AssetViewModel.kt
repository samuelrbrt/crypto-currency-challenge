package com.yoti.android.cryptocurrencychallenge.asset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.yoti.android.cryptocurrencychallenge.config.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(repo: AssetRepository) : BaseViewModel() {
    val assets = repo.getAssets()

    private val refresh = MutableLiveData(false)
    val assetRefreshState = refresh.switchMap { _ -> liveData { emit(repo.refreshAsset()) } }

    fun refreshAssets() {
        refresh.value = true
    }
}