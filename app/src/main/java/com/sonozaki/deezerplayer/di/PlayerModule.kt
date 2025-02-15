package com.sonozaki.deezerplayer.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.media3.common.MediaItem
import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.controller.domain.repository.PlayerControllerRepository
import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.data.player.entities.PlayerEventData
import com.sonozaki.data.player.entities.PlayerStateData
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.data.player.repositories.PlayerEventsRepository
import com.sonozaki.data.player.repositories.PlayerEventsRepositoryImpl
import com.sonozaki.data.player.repositories.PlayerUIRepository
import com.sonozaki.data.player.repositories.PlayerUIRepositoryImpl
import com.sonozaki.deezerplayer.MainActivity
import com.sonozaki.deezerplayer.adapters.player.PlayerControllerRepositoryAdapter
import com.sonozaki.deezerplayer.adapters.player.PlayerScreenRepositoryAdapter
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.deezerplayer.mappers.player.MediaItemToPlayerDataMapper
import com.sonozaki.deezerplayer.mappers.player.PlayerDataToUIErrorMapper
import com.sonozaki.deezerplayer.mappers.player.PlayerErrorToDataErrorMapper
import com.sonozaki.deezerplayer.mappers.player.PlayerEventDataToEventMapper
import com.sonozaki.deezerplayer.mappers.player.PlayerStateDataToPlayerStateMapper
import com.sonozaki.deezerplayer.mappers.player.TrackInfoToMediaItemMapper
import com.sonozaki.player.domain.entities.CurrentTrackState
import com.sonozaki.player.domain.repositories.PlayerScreenRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerModule {

    @Binds
    @Singleton
    abstract fun bindPlayerControllerRepository(repositoryImpl: PlayerControllerRepositoryAdapter): PlayerControllerRepository


    @Binds
    @Singleton
    abstract fun bindPlayerUiRepository(repositoryImpl: PlayerUIRepositoryImpl): PlayerUIRepository

    @Binds
    @Singleton
    abstract fun bindPlayerEventsRepository(repositoryImpl: PlayerEventsRepositoryImpl): PlayerEventsRepository

    @Binds
    @Singleton
    abstract fun bindPlayerScreenRepository(repositoryImpl: PlayerScreenRepositoryAdapter): PlayerScreenRepository

    @Binds
    @Singleton
    abstract fun bindMediaItemToTrackInfoMapper(mapperImpl: MediaItemToPlayerDataMapper): Mapper<MediaItem, TrackInfo>

    @Binds
    @Singleton
    abstract fun bindPlayerEventDataToEvent(mapper: PlayerEventDataToEventMapper): Mapper<PlayerEventData, PlayerEvent>

    @Binds
    @Singleton
    abstract fun bindPlayerDataToUIErrorMapper(mapper: PlayerDataToUIErrorMapper): Mapper<PlayerErrorData, UIError>

    @Binds
    @Singleton
    abstract fun bindPlayerErrorToDataErrorMapper(mapper: PlayerErrorToDataErrorMapper): Mapper<PlayerError, PlayerErrorData>

    @Binds
    @Singleton
    abstract fun bindPlayerStateDataToPlayerStateMapper(mapper: PlayerStateDataToPlayerStateMapper): Mapper<PlayerStateData, CurrentTrackState>

    @Binds
    @Singleton
    abstract fun bindTrackInfoToMediaItemMapper(mapper: TrackInfoToMediaItemMapper): Mapper<TrackInfo, MediaItem>

    companion object {
        @Provides
        @Singleton
        fun provideMainActivityIntent(@ApplicationContext context: Context): PendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        @Provides
        @Singleton
        fun provideStateDataFlow(): MutableStateFlow<PlayerStateData> =
            MutableStateFlow(PlayerStateData.Loading)

        @Provides
        @Singleton
        fun provideEventsChannel(): Channel<PlayerEventData> = Channel()

        @Provides
        @Singleton
        fun provideErrorEventsChannel(): Channel<PlayerErrorData> = Channel()
    }
}