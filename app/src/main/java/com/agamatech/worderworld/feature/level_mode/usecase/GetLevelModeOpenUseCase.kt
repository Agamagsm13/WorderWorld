package com.agamatech.worderworld.feature.level_mode.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class GetLevelModeOpenUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(): Boolean {
        return localStorage.levelModeOpen
    }
}