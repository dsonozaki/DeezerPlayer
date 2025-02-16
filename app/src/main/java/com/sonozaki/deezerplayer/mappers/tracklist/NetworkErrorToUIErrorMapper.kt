package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.core.presentation.UIError
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.features.deezertracks.R
import com.sonozaki.network.NetworkError
import javax.inject.Inject

class NetworkErrorToUIErrorMapper @Inject constructor() : Mapper<NetworkError, UIError> {
    override fun map(t1: NetworkError) = when (t1) {
        is NetworkError.ServerError -> UIError(R.string.server_error)
        is NetworkError.ConnectionError -> UIError(R.string.connection_error)
        is NetworkError.UnknownError -> UIError(R.string.unknown_error, t1.error)
        is NetworkError.EmptyResponse -> UIError(R.string.empty_response)
    }
}