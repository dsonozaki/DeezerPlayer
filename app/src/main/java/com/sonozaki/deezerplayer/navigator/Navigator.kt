package com.sonozaki.deezerplayer.navigator

import androidx.navigation.NavController
import com.sonozaki.deezerplayer.R
import com.sonozaki.features.deezertracks.DeezerRouter
import com.sonozaki.features.localtracks.presentation.LocalTrackRouter
import javax.inject.Inject

/**
 * Navigator connecting all app's destinations
 */
class Navigator @Inject constructor() : LocalTrackRouter, DeezerRouter {

    override fun openPlayerFromLocal(navController: NavController) {
        navController.navigate(R.id.action_localTracksFragment2_to_playerFragment2)
    }

    override fun openPlayerFromDeezer(navController: NavController) {
        navController.navigate(R.id.action_deezerTracksFragment_to_playerFragment)
    }
}