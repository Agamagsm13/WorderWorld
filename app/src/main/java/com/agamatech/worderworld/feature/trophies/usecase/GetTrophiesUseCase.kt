package com.agamatech.worderworld.feature.trophies.usecase

import com.agamatech.worderworld.domain.data.Trophy
import javax.inject.Inject

class GetTrophiesUseCase @Inject constructor() {
    operator fun invoke(): List<Trophy> {
        return Trophy.allDefault().toList()
    }
}