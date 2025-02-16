package com.sonozaki.deezertracks.entitities

import com.sonozaki.deezertracks.network.dto.TrackDto
import com.sonozaki.network.NetworkError

sealed class NetworkTrackStateData {
    data object Loading : NetworkTrackStateData()
    data class Data(val tracks: List<TrackDto>) : NetworkTrackStateData()
    data class Error(val error: NetworkError) : NetworkTrackStateData()
}