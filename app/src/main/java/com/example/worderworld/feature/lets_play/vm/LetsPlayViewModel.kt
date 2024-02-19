package com.agamatech.worderworld.feature.lets_play.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agamatech.worderworld.domain.data.Ball
import com.agamatech.worderworld.feature.lets_play.usecase.GetBestScoreUseCase
import com.agamatech.worderworld.feature.store.usecase.GetSelectedBallUseCase
import com.agamatech.worderworld.feature.store.usecase.SelectBallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LetsPlayViewModel @Inject constructor(
    private val getBestScoreUseCase: GetBestScoreUseCase,
    private val getSelectedBallUseCase: GetSelectedBallUseCase
): ViewModel() {

    private val _selectedBall = MutableLiveData<String>()
    val selectedBall: LiveData<String> get() = _selectedBall

    private val _bestScore = MutableLiveData<Int>()
    val bestScore: LiveData<Int> = _bestScore

    fun getBestScore() {
        _bestScore.value = getBestScoreUseCase()
    }

    fun getSelectedBall() {
        _selectedBall.value = getSelectedBallUseCase()
    }
}