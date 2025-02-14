package com.sonozaki.player.domain.repositories

import com.sonozaki.core.presentation.UIError
import com.sonozaki.player.domain.entities.CurrentTrackState
import kotlinx.coroutines.flow.Flow

interface PlayerScreenRepository {
    /**
     * Current music in player
     */
    val playerState: Flow<CurrentTrackState>

    /**
     * Player errors
     */
    val errorEvents: Flow<UIError>
}