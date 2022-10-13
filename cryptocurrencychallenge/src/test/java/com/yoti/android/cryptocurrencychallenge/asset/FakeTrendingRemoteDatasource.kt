package com.yoti.android.cryptocurrencychallenge.asset

class FakeTrendingRemoteDatasource(var repositories: List<GithubRepo> = emptyList()): TrendingApi {
    override suspend fun getTrendingRepositories() = repositories
}