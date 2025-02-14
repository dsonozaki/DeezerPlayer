package com.sonozaki.controller.domain.usecases

import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.controller.domain.repository.PlayerControllerRepository
import javax.inject.Inject

class SendExceptionUseCase @Inject constructor(private val repository: PlayerControllerRepository) {
    suspend operator fun invoke(exception: PlayerError) {
        repository.sendException(exception)
    }
}