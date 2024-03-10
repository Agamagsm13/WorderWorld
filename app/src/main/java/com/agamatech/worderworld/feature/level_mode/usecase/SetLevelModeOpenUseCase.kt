package com.agamatech.worderworld.feature.level_mode.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class SetLevelModeOpenUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(value: Boolean) {
        localStorage.levelModeOpen = value
    }
}