package com.testtask.events.network

import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

class NetworkSourceImpl : NetworkSource {

    override suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): Error? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder()
                    .build()
                    .adapter(Error::class.java)

                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}