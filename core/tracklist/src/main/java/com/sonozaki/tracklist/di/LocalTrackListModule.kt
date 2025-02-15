package com.sonozaki.tracklist.di

import coil3.transform.RoundedCornersTransformation
import coil3.transform.Transformation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalTrackListModule {
    @Provides
    @Named("smallIcon")
    @Singleton
    fun provideTransformation(): Transformation = RoundedCornersTransformation(8f)
}