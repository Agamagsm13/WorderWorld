package com.agamatech.worderworld.feature.lets_play.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agamatech.worderworld.feature.lets_play.usecase.GetWordsGuessedUseCase
import com.agamatech.worderworld.feature.store.usecase.GetSelectedBallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LetsPlayViewModel @Inject constructor(
    private val getWordsGuessedUseCase: GetWordsGuessedUseCase,
    private val getSelectedBallUseCase: GetSelectedBallUseCase
): ViewModel() {

    private val _letterCount = MutableLiveData<Int>()
    val letterCount: LiveData<Int> get() = _letterCount

    private val _wordsGuessed = MutableLiveData<Int>()
    val wordsGuessed: LiveData<Int> = _wordsGuessed

    fun getWordsGuessed() {
        _wordsGuessed.value = getWordsGuessedUseCase()
    }

    fun addLetterCount() {
        _letterCount.value = (_letterCount.value?: 0) + 1
    }

    fun minusLetterCount() {
        _letterCount.value = (_letterCount.value?: 0) - 1
    }

    fun setLetterCount(value: Int) {
        _letterCount.value = value
    }
}