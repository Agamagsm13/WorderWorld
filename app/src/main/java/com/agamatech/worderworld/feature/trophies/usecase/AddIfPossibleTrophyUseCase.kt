package com.agamatech.worderworld.feature.trophies.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import com.agamatech.worderworld.domain.data.Trophy
import javax.inject.Inject

class AddIfPossibleTrophyUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(id: String): Trophy? {
        val achievedTrophies = ((localStorage.trophies?.split(","))?: listOf()).map {
            Trophy.findById(it)
        }
        if (achievedTrophies.any { id == it?.id}) return null
        val newTrophy = Trophy.findById(id) ?: return null
        localStorage.trophies = (localStorage.trophies?:"") + "$id,"
        return newTrophy
    }
}