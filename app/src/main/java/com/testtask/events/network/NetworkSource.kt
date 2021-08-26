package com.testtask.events.network

interface NetworkSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T>

}