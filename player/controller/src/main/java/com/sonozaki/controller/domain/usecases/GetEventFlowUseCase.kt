package com.sonozaki.controller.domain.usecases

import com.sonozaki.controller.domain.repository.PlayerControllerRepository
import javax.inject.Inject

class GetEventFlowUseCase @Inject constructor(private val repository: PlayerControllerRepository) {
    operator fun invoke() = repository.eventFlow
}