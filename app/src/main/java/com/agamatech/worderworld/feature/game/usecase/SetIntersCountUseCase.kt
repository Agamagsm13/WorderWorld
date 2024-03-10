package com.agamatech.worderworld.feature.game.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class SetIntersCountUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke() {
        localStorage.intersCount = (localStorage.intersCount + 1) % 2
    }
}