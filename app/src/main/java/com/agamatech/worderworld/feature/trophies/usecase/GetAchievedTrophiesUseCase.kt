package com.agamatech.worderworld.feature.trophies.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import com.agamatech.worderworld.domain.data.Trophy
import javax.inject.Inject

class GetAchievedTrophiesUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(): List<Trophy?> {
        return ((localStorage.trophies?.split(",")?.filter { it.isNotEmpty() })?: listOf()).map {
            Trophy.findById(it)
        }
    }
}