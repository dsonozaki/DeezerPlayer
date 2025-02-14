package com.sonozaki.controller.domain.usecases

import androidx.media3.common.MediaItem
import com.sonozaki.controller.domain.repository.PlayerControllerRepository
import javax.inject.Inject

class SetCurrentTrackUIUseCase @Inject constructor(private val repository: PlayerControllerRepository) {
    suspend operator fun invoke(track: MediaItem) {
        repository.setCurrentTrack(track)
    }
}