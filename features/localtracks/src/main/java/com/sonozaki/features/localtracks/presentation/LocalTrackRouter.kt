package com.sonozaki.features.localtracks.presentation

import androidx.navigation.NavController

/**
 * Interface for navigation to player
 */
interface LocalTrackRouter {
    fun openPlayerFromLocal(navController: NavController)
}