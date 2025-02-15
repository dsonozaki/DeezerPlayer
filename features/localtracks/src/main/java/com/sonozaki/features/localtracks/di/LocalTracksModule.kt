package com.sonozaki.features.localtracks.di

import com.sonozaki.features.localtracks.presentation.events.Event
import com.sonozaki.features.localtracks.presentation.viewmodel.LocalTracksViewModel.Companion.PERMISSION_DENIED
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.Channel
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class LocalTracksModule {
    @Provides
    @ViewModelScoped
    fun providePermissionEventChannel(): Channel<Event> = Channel()

    @Provides
    @Named(PERMISSION_DENIED)
    @ViewModelScoped
    fun providePermissionDeniedChannel(): Channel<Unit> = Channel()
}