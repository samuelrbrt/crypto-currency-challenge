package com.yoti.android.cryptocurrencychallenge.config.init.fake

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoti.android.cryptocurrencychallenge.asset.Asset
import java.io.IOException

class FakeAssetPagingSource(private val isError: Boolean = false, private var assets: List<Asset>? = null) :
    PagingSource<Int, Asset>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Asset> {
        if (isError) return LoadResult.Error(IOException())
        return LoadResult.Page(assets!!, prevKey = null, nextKey = null)
    }

    override fun getRefreshKey(state: PagingState<Int, Asset>): Int? {
        return state.anchorPosition
    }
}