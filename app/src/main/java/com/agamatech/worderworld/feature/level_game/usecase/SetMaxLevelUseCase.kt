package com.agamatech.worderworld.feature.level_game.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class SetMaxLevelUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(value: Int) {
        localStorage.maxLevel = value
    }
}