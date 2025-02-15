package com.sonozaki.tracklist.domain.usecases

import com.sonozaki.tracklist.domain.repository.TracksRepository
import javax.inject.Inject

class RefreshUseCaseImpl @Inject constructor(private val repository: TracksRepository) :
    RefreshUseCase {
    override suspend operator fun invoke(searchQuery: String) {
        repository.refresh(searchQuery)
    }
}