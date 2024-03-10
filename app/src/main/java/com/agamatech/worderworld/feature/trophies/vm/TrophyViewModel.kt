package com.agamatech.worderworld.feature.trophies.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agamatech.worderworld.domain.data.Trophy
import com.agamatech.worderworld.feature.trophies.usecase.GetAchievedTrophiesUseCase
import com.agamatech.worderworld.feature.trophies.usecase.GetTrophiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrophyViewModel @Inject constructor(
    private val getTrophiesUseCase: GetTrophiesUseCase,
    private val getAchievedTrophiesUseCase: GetAchievedTrophiesUseCase,
): ViewModel() {
    private val _allTrophies = MutableLiveData<List<Trophy>>()
    val allTrophies: LiveData<List<Trophy>> get() = _allTrophies

    private val _achievedTrophies = MutableLiveData<List<Trophy?>>()
    val achievedTrophies: LiveData<List<Trophy?>> get() = _achievedTrophies


    fun getAllTrophies() {
        _allTrophies.value = getTrophiesUseCase()
    }

    fun getAchievedTrophies() {
        _achievedTrophies.value = getAchievedTrophiesUseCase()
    }

}