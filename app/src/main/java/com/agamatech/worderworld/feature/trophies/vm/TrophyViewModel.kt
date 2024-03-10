package com.agamatech.worderworld.feature.trophies.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agamatech.worderworld.domain.data.Trophy
import com.agamatech.worderworld.feature.trophies.usecase.GetTrophiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrophyViewModel @Inject constructor(
    private val getTrophiesUseCase: GetTrophiesUseCase
): ViewModel() {
    private val _trophiesList = MutableLiveData<List<Trophy>>()
    val trophiesList: LiveData<List<Trophy>> get() = _trophiesList


    fun getAllTrophies() {
        _trophiesList.value = getTrophiesUseCase()
    }

}