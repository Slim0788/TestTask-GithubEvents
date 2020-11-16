package com.testtask.events.repository

import com.testtask.events.api.GithubServiceApi
import com.testtask.events.api.dto.ResponseDto
import com.testtask.events.network.NetworkSource
import com.testtask.events.network.ResultWrapper
import com.testtask.events.repository.cache.EventsCache

class EventsRepositoryImpl(
    private val githubServiceApi: GithubServiceApi,
    private val networkSource: NetworkSource,
    private val cache: EventsCache
) : EventsRepository {

    override suspend fun getData(): ResultWrapper<List<ResponseDto>> {
        if (cache.isCached()) {
            return ResultWrapper.Success(cache.getResponse())
        }
        val result = networkSource.safeApiCall {
            githubServiceApi.getEvents()
        }

        if (result is ResultWrapper.Success) {
            cache.insertResponse(result.value)
        }

        return result
    }

    override fun deleteCachedData() {
        cache.clearCache()
    }

}