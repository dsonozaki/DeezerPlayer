package com.sonozaki.deezerplayer.di

import android.graphics.drawable.Drawable
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class PlayerFragmentModule {
    @Provides
    @FragmentScoped
    fun providePlaceholderDrawable(): Drawable = ShimmerDrawable().apply {
        setShimmer(
            Shimmer.AlphaHighlightBuilder()
                .setBaseAlpha(1f)
                .setHighlightAlpha(0.5f)
                .setDuration(1800)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
        )
    }
}