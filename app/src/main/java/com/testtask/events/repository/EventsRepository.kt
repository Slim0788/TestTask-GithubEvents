package com.testtask.events.repository

import com.testtask.events.api.dto.ResponseDto
import com.testtask.events.network.ResultWrapper

interface EventsRepository {

    suspend fun getData(): ResultWrapper<List<ResponseDto>>

    fun deleteCachedData()
}