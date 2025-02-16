package com.sonozaki.network

sealed class NetworkError {
    data object EmptyResponse: NetworkError()
    data object ServerError: NetworkError()
    data object ConnectionError: NetworkError()
    data class UnknownError(val error: String): NetworkError()
}