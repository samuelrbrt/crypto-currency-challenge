package com.yoti.android.cryptocurrencychallenge.asset

import com.yoti.android.cryptocurrencychallenge.config.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultTrendingRepositoryTest {
    private lateinit var repo: DefaultTrendingRepository
    private lateinit var remoteDatasource: FakeTrendingRemoteDatasource
    private lateinit var localDataSource: FakeTrendingLocalDataSource
    private lateinit var scope: TestScope
    private lateinit var dispatchers: TestDispatchers

    @Before
    fun createRepository() {
        remoteDatasource = FakeTrendingRemoteDatasource()
        localDataSource = FakeTrendingLocalDataSource()
        dispatchers = TestDispatchers()
        repo = DefaultTrendingRepository(remoteDatasource, localDataSource, dispatchers)
        scope = TestScope(dispatchers.Unconfined)
    }

    @Test
    fun `new repository will have empty trending repos`() = scope.runTest {
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first(), `is`(emptyList()))
    }

    @Test
    fun `non empty repo will have trending repos`() = scope.runTest {
        val repos = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        localDataSource.repositories = repos
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first(), `is`(repos))
    }

    @Test
    fun `sort with name will return sorted list by name in ascending order`() = scope.runTest {
        val repos = listOf(
            GithubRepo("zebra", "auth", "avatar", "desc", "lang", "color", 0, 0),
            GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0)
        )

        localDataSource.repositories = repos
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first()[0], `is`(repos[1]))
    }

    @Test
    fun `sort with forks will return sorted list by fork in descending order`() = scope.runTest {
        val repos = listOf(
            GithubRepo("zebra", "auth", "avatar", "desc", "lang", "color", 0, 0),
            GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 1)
        )

        localDataSource.repositories = repos
        assertThat(repo.getTrendingRepositories(SortBy.FORKS).first()[0], `is`(repos[1]))
    }

    @Test
    fun `sort with stars will return sorted list by stars in descending order`() = scope.runTest {
        val repos = listOf(
            GithubRepo("zebra", "auth", "avatar", "desc", "lang", "color", 0, 0),
            GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 5, 1)
        )

        localDataSource.repositories = repos
        assertThat(repo.getTrendingRepositories(SortBy.STARS).first()[0], `is`(repos[1]))
    }

    @Test
    fun emptyRepo_InvalidCache() = scope.runTest {
        assertThat(repo.isCacheValid("abc"), `is`(false))
    }

    @Test
    fun `0ms ttl will expire after 0ms delay`() = scope.runTest {
        val ttl = ApiCacheTTL("abc", Calendar.getInstance().timeInMillis, 0)
        localDataSource.upsertApiCacheTTL(ttl)
        assertThat(repo.isCacheValid("abc"), `is`(false))
    }

    @Test
    fun `2ms ttl will not expire after 0 delay`() = scope.runTest {
        val ttl = ApiCacheTTL("abc", Calendar.getInstance().timeInMillis, 2)
        localDataSource.upsertApiCacheTTL(ttl)
        assertThat(repo.isCacheValid("abc"), `is`(true))
    }

    @Test
    fun `empty repo will populate the repo`() = scope.runTest {
        val repos = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        remoteDatasource.repositories = repos.toMutableList()

        repo.refreshTrendingRepositories(false)
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first(), `is`(repos))
    }

    @Test
    fun `expired cache will refresh from the network`() = scope.runTest {
        val localData = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        val ttl =
            ApiCacheTTL(DefaultTrendingRepository.Companion.CACHE_KEY_REPOSITORIES, Calendar.getInstance().timeInMillis, 0)
        localDataSource.cleansertRepositories(localData, ttl)

        val remoteData = listOf(GithubRepo("remote", "remote", "remote", "remote", "remote", "remote", 2, 2))
        remoteDatasource.repositories = remoteData

        repo.refreshTrendingRepositories(false)
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first(), `is`(remoteData))
    }

    @Test
    fun `force refresh will fetch data from the remote datasource`() = scope.runTest {
        val localData = listOf(GithubRepo("name", "auth", "avatar", "desc", "lang", "color", 0, 0))
        localDataSource.repositories = localData

        val remoteData = listOf(GithubRepo("remote", "remote", "remote", "remote", "remote", "remote", 2, 2))
        remoteDatasource.repositories = remoteData

        repo.refreshTrendingRepositories(true)
        assertThat(repo.getTrendingRepositories(SortBy.NAME).first(), `is`(remoteData))
    }
}