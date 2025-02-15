package com.sonozaki.deezerplayer.di

import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.localtracks.entitities.TrackData
import com.sonozaki.data.localtracks.entitities.TrackError
import com.sonozaki.data.localtracks.entitities.TracksStateData
import com.sonozaki.data.localtracks.repository.LocalTracksRepository
import com.sonozaki.data.localtracks.repository.LocalTracksRepositoryImpl
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.deezerplayer.adapters.tracklist.LocalTracksRepositoryAdapter
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.deezerplayer.mappers.tracklist.TrackDataToTrackMapper
import com.sonozaki.deezerplayer.mappers.tracklist.TrackErrorToUIErrorMapper
import com.sonozaki.deezerplayer.mappers.tracklist.TrackStateDataToTrackStateMapper
import com.sonozaki.deezerplayer.mappers.tracklist.TrackToTrackInfoMapper
import com.sonozaki.deezerplayer.navigator.Navigator
import com.sonozaki.features.localtracks.presentation.LocalTrackRouter
import com.sonozaki.features.localtracks.presentation.viewmodel.LocalTracksViewModel.Companion.INJECTION_NAME
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.usecases.GetTrackUseCaseImpl
import com.sonozaki.tracklist.domain.usecases.GetTracksUseCase
import com.sonozaki.tracklist.domain.usecases.RefreshUseCase
import com.sonozaki.tracklist.domain.usecases.RefreshUseCaseImpl
import com.sonozaki.tracklist.domain.usecases.SetTrackListAndTrackUseCase
import com.sonozaki.tracklist.domain.usecases.SetTrackListAndTrackUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TracksModule {

    @Binds
    @Singleton
    abstract fun bindLocalTrackFragmentNavigator(navigator: Navigator): LocalTrackRouter

    @Binds
    @Singleton
    abstract fun bindTrackDataToTrackMapper(trackDataToTrackMapper: TrackDataToTrackMapper): Mapper<TrackData, Track>

    @Binds
    @Singleton
    abstract fun bindTrackErrorTiUIErrorMapper(trackErrorToUIErrorMapper: TrackErrorToUIErrorMapper): Mapper<TrackError, UIError>

    @Binds
    @Singleton
    abstract fun bindTrackStateDataToTrackState(trackStateDataToTrackStateMapper: TrackStateDataToTrackStateMapper): Mapper<TracksStateData, TrackState>

    @Binds
    @Singleton
    abstract fun bindTrackToTrackInfoMapper(trackToTrackInfoMapper: TrackToTrackInfoMapper): Mapper<Track, TrackInfo>

    @Binds
    @Singleton
    abstract fun bindLocalTracksRepository(localTracksRepository: LocalTracksRepositoryImpl): LocalTracksRepository

    companion object {
        @Provides
        @Singleton
        @Named(INJECTION_NAME)
        fun provideRefreshDataUseCase(localTracksRepositoryAdapter: LocalTracksRepositoryAdapter): RefreshUseCase =
            RefreshUseCaseImpl(localTracksRepositoryAdapter)

        @Provides
        @Singleton
        @Named(INJECTION_NAME)
        fun provideGetTracksUseCase(localTracksRepositoryAdapter: LocalTracksRepositoryAdapter): GetTracksUseCase =
            GetTrackUseCaseImpl(localTracksRepositoryAdapter)

        @Provides
        @Singleton
        @Named(INJECTION_NAME)
        fun provideSetTrackListUseCase(localTracksRepositoryAdapter: LocalTracksRepositoryAdapter): SetTrackListAndTrackUseCase =
            SetTrackListAndTrackUseCaseImpl(localTracksRepositoryAdapter)

        @Provides
        @Singleton
        fun provideTrackStateChannel(): Channel<TracksStateData> = Channel()


    }
}