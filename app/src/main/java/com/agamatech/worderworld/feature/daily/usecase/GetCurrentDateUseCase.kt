package com.agamatech.worderworld.feature.daily.usecase

import com.agamatech.worderworld.repo.MainRepo
import javax.inject.Inject

class GetCurrentDateUseCase @Inject constructor(
    private val mainRepo: MainRepo
) {
    suspend operator fun invoke(): String {
        return mainRepo.getTime().await()
    }
}