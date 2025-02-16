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

@Module
@InstallIn(ViewModelComponent::class)
class MainVMModule {

    @Provides
    @ViewModelScoped
    fun providePlayerController(@ApplicationContext context: Context,
                                sessionToken: SessionToken,
                                getEventFlowUseCase: GetEventFlowUseCase,
                                playerListener: PlayerListener): PlayerController = PlayerController(
                                    context, sessionToken, getEventFlowUseCase, playerListener
                                )
}