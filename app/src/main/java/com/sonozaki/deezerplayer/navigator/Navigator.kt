package com.sonozaki.deezerplayer.navigator

import androidx.navigation.NavController
import com.sonozaki.deezerplayer.R
import com.sonozaki.features.localtracks.presentation.LocalTrackRouter
import javax.inject.Inject

/**
 * Navigator connecting all app's destinations
 */
class Navigator @Inject constructor() : LocalTrackRouter {

    override fun openPlayerFromLocal(navController: NavController) {
        navController.navigate(R.id.action_localTracksFragment2_to_playerFragment2)
    }
}