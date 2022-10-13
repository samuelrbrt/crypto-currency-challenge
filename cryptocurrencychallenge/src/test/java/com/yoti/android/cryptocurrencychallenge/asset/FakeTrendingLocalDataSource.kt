package com.yoti.android.cryptocurrencychallenge.asset

import com.example.github.config.api.ApiCacheTTL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeTrendingLocalDataSource(
    var repositories: List<GithubRepo> = emptyList()
) : TrendingDao {
    private val caches = LinkedHashMap<String, ApiCacheTTL>()

    override fun getTrendingRepositoriesOrderByName(): Flow<List<GithubRepo>> =
        flow { emit(repositories) }.map { repos -> repos.sortedBy { it.name } }

    override fun getTrendingRepositoriesOrderByStar() =
        flow { emit(repositories) }.map { repos -> repos.sortedByDescending { it.stars } }

    override fun getTrendingRepositoriesOrderByFork() =
        flow { emit(repositories) }.map { repos -> repos.sortedByDescending { it.forks } }

    override suspend fun upsertRepositories(repos: List<GithubRepo>) {
        cleanRepositories()

        repositories = repos
    }

    override suspend fun cleanRepositories() {
        repositories = emptyList()
    }

    override suspend fun upsertApiCacheTTL(cacheTTL: ApiCacheTTL) {
        caches[cacheTTL.path] = cacheTTL
    }

    override suspend fun getCacheTTL(path: String) = caches[path]
}