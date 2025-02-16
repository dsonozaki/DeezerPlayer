package com.sonozaki.deezertracks.repository

import com.sonozaki.deezertracks.entitities.NetworkTrackStateData
import com.sonozaki.deezertracks.network.services.ChartService
import com.sonozaki.deezertracks.network.services.SearchService
import com.sonozaki.network.RequestResult
import com.sonozaki.network.safeApiCall
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class DeezerTracksRepositoryImpl @Inject constructor(
    private val chartService: ChartService,
    private val searchService: SearchService,
    private val channel: Channel<NetworkTrackStateData>
) : DeezerTracksRepository {

    override val tracks: Flow<NetworkTrackStateData>
        get() = channel.receiveAsFlow()


    private suspend fun getChartsData() {
        val result = safeApiCall {
            chartService.getCharts()
        }
        when (result) {
            is RequestResult.Data -> {
                channel.send(NetworkTrackStateData.Data(result.data.tracks.data))
            }

            is RequestResult.Error -> {
                channel.send(NetworkTrackStateData.Error(result.error))
            }
        }
    }

    private suspend fun getSearchData(query: String) {
        val result = safeApiCall {
            searchService.getSearchResult(query)
        }
        when (result) {
            is RequestResult.Data -> {
                channel.send(NetworkTrackStateData.Data(result.data.data))
            }

            is RequestResult.Error -> {
                channel.send(NetworkTrackStateData.Error(result.error))
            }
        }
    }

    /**
     * Load tracks from chart if query is empty, search tracks otherwise
     */
    override suspend fun refreshData(query: String) {
        channel.send(NetworkTrackStateData.Loading)
        if (query.isBlank()) {
            getChartsData()
        } else {
            getSearchData(query)
        }
    }

}