package com.agamatech.worderworld.feature.daily.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import javax.inject.Inject

class GetWordsGuessedRowUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) { operator fun invoke(): Int {
        return localStorage.wordsGuessedRow
    }
}