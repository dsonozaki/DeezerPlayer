package com.sonozaki.tracklist.domain.usecases

interface RefreshUseCase {
    suspend operator fun invoke(searchQuery: String)
}