package com.sonozaki.player.di

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
class LocalPlayerModule {
    @Provides
    @Named(BIG_ICON_TRANSFORMATION)
    @Singleton
    fun provideTransformation(): Transformation = RoundedCornersTransformation(20f)

    companion object {
        const val BIG_ICON_TRANSFORMATION = "bigIcon"
    }
}