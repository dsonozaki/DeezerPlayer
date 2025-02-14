package com.sonozaki.player.domain.usecases

import com.sonozaki.player.domain.repositories.PlayerScreenRepository
import javax.inject.Inject

class GetErrorEventsUseCase @Inject constructor(private val repository: PlayerScreenRepository) {
    operator fun invoke() = repository.errorEvents
}