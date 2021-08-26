package com.testtask.events.repository.cache

import com.testtask.events.api.dto.ResponseDto
import com.testtask.events.db.ResponseDao
import java.util.concurrent.TimeUnit

private val EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10)

class EventsCacheImpl constructor(
    private val responseDao: ResponseDao,
    private val preferenceHelper: SharedPreferencesApi
) : EventsCache {

    override fun isCached(): Boolean {
        return responseDao.getAll().isNotEmpty() && !isExpired()
    }

    private fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()

        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferenceHelper.lastCacheTime
    }

    override fun getResponse(): List<ResponseDto> {
        return responseDao.getAll()
    }

    override fun insertResponse(response: List<ResponseDto>) {
        responseDao.deleteAll()
        responseDao.insertAll(response)

        preferenceHelper.lastCacheTime = System.currentTimeMillis()
    }

    override fun clearCache() {
        responseDao.deleteAll()
    }
}