package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.localtracks.entitities.TrackError
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.features.localtracks.R
import javax.inject.Inject

class TrackErrorToUIErrorMapper @Inject constructor() : Mapper<TrackError, UIError> {
    override fun map(t1: TrackError) = when (t1) {
        is TrackError.CustomException -> UIError(R.string.custom_exception)
        is TrackError.DataNotRetrieved -> UIError(R.string.data_not_retrieved)
    }
}