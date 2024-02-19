package com.agamatech.worderworld.feature.lets_play.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class GetBestScoreUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(): Int {
        return localStorage.bestScore
    }
}