package com.yoti.android.cryptocurrencychallenge.asset

import com.example.github.config.state.State
import com.yoti.android.cryptocurrencychallenge.asset.FakeTrendingLocalDataSource

class FakeTrendingRepository : TrendingRepository {

    var repos: List<GithubRepo> = emptyList()
        set(value) {
            field = value
            localDataSource = FakeTrendingLocalDataSource(value)
        }

    private var localDataSource: FakeTrendingLocalDataSource = FakeTrendingLocalDataSource(repos)

    override fun getTrendingRepositories(sortBy: SortBy) = localDataSource.getTrendingRepositories(sortBy)

    override suspend fun refreshTrendingRepositories(isForceRefresh: Boolean): State<List<GithubRepo>> {
        return State.Success(repos)
    }

    override suspend fun isCacheValid(path: String) = false
}