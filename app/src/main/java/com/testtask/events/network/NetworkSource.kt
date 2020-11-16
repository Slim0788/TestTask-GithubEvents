package com.testtask.events.network

interface NetworkSource {

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T>

}