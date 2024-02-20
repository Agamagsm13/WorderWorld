package com.agamatech.worderworld.feature.game.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class SetWordsGuessedUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(value: Int) {
        localStorage.wordsGuessed = value
    }
}