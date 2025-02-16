package com.sonozaki.features.deezertracks.di

import com.sonozaki.features.deezertracks.presentation.events.Event
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.Channel

@Module
@InstallIn(ViewModelComponent::class)
class LocalDeezerTrackModule {
    @Provides
    @ViewModelScoped
    fun providePermissionEventChannel(): Channel<Event> = Channel()
}