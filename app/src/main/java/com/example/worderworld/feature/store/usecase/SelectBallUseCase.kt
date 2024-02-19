package com.agamatech.worderworld.feature.store.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import com.agamatech.worderworld.domain.data.Ball
import javax.inject.Inject

class SelectBallUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(id: String) {
        localStorage.selectedBall = id
    }
}