package com.sonozaki.tracklist.domain.usecases

import com.sonozaki.tracklist.domain.entities.TrackState
import kotlinx.coroutines.flow.Flow

interface GetTracksUseCase {
    operator fun invoke(): Flow<TrackState>
}