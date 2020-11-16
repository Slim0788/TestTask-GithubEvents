package com.testtask.events.repository.cache

import com.testtask.events.api.dto.ResponseDto

interface EventsCache {

    fun isCached(): Boolean

    fun getResponse(): List<ResponseDto>

    fun insertResponse(response: List<ResponseDto>)

    fun clearCache()
}