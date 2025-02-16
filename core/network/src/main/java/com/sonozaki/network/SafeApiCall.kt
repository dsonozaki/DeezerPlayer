package com.sonozaki.network

import retrofit2.Response
import java.io.IOException

/**
 * Retrieving API request result in a safe way, mapping network exceptions to NetworkError instances
 */
suspend fun <T> safeApiCall(unsafeCall: suspend () -> Response<T>): RequestResult<T> {
    return try {
        val response = unsafeCall()
        if (response.isSuccessful) {
            val body = response.body()
            return if (body == null) {
                RequestResult.Error(NetworkError.EmptyResponse)
            } else {
                RequestResult.Data(body)
            }
        }
        return RequestResult.Error(NetworkError.ServerError)
    } catch (e: IOException) {
        RequestResult.Error(NetworkError.ConnectionError)
    } catch (e: Exception) {
        RequestResult.Error(NetworkError.UnknownError(e.message ?: ""))
    }
}