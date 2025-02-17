package com.sonozaki.deezerplayer.di

import android.content.Context
import androidx.media3.session.SessionToken
import com.sonozaki.controller.PlayerController
import com.sonozaki.controller.PlayerListener
import com.sonozaki.controller.domain.usecases.GetEventFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class MainVMModule {

    @Provides
    @ViewModelScoped
    fun providePlayerController(@ApplicationContext context: Context,
                                sessionToken: SessionToken,
                                getEventFlowUseCase: GetEventFlowUseCase,
                                playerListener: PlayerListener,
                                @Named("IODispatcher") ioDispatcher: CoroutineDispatcher): PlayerController = PlayerController(
                                    context, sessionToken, getEventFlowUseCase, playerListener, ioDispatcher
                                )
}