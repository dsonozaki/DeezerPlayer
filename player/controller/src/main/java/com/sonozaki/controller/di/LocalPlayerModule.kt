package com.sonozaki.controller.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.SessionToken
import com.sonozaki.controller.PlayerController
import com.sonozaki.controller.presentation.service.PlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalPlayerModule {

    /**
     * Provide media controller. Must be called from background using [dagger.Lazy]
     */
    @Provides
    @Singleton
    fun providePlayer(
        @ApplicationContext context: Context,
        sessionToken: SessionToken
    ): Player = PlayerController.buildMediaController(context, sessionToken)

    @Provides
    @Singleton
    fun provideSessionToken(@ApplicationContext context: Context) =
        SessionToken(context, ComponentName(context, PlayerService::class.java))
}