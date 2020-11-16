package com.testtask.events.api

import com.testtask.events.api.dto.ResponseDto
import retrofit2.http.GET

interface GithubServiceApi {

    @GET("events")
    suspend fun getEvents(): List<ResponseDto>

}