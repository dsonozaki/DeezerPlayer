package com.sonozaki.tracklist.presentation.utils

/**
 * Format track duration in mm:ss format
 */
fun formatMilliseconds(ms: Long): String {
    val minutes = (ms / 60_000) % 60
    val seconds = (ms % 60_000) / 1_000

    return String.format("%02d:%02d", minutes, seconds)
}