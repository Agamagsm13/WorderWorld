package com.agamatech.worderworld.feature.game.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamatech.worderworld.feature.game.usecase.GetIntersCountUseCase
import com.agamatech.worderworld.feature.game.usecase.SetBestScoreUseCase
import com.agamatech.worderworld.feature.game.usecase.SetIntersCountUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetBestScoreUseCase
import com.agamatech.worderworld.feature.store.usecase.GetSelectedBallUseCase
import com.agamatech.worderworld.utils.dpToPx
import com.example.worderworld.event.CheckWordPressEvent
import com.example.worderworld.feature.LoadWordManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val setBestScoreUseCase: SetBestScoreUseCase,
    private val getBestScoreUseCase: GetBestScoreUseCase,
    private val getSelectedBallUseCase: GetSelectedBallUseCase,
    private val getIntersCountUseCase: GetIntersCountUseCase,
    private val setIntersCountUseCase: SetIntersCountUseCase,
    private val loadWordManager: LoadWordManager,
): ViewModel() {

    private val _passedTries = MutableLiveData<MutableList<String>>()
    val passedTries: LiveData<MutableList<String>> = _passedTries

    private val _wordValue = MutableLiveData<String>()
    val wordValue: LiveData<String> = _wordValue

    private val _activeTry = MutableLiveData<Int>()
    val activeTry: LiveData<Int> = _activeTry

    private val _activeLetter = MutableLiveData<Int>()
    val activeLetter: LiveData<Int> = _activeLetter

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    init {
        _activeTry.value = 0
        _activeLetter.value = 0
        _passedTries.value = mutableListOf()
    }
    fun setWord(word: String) {
        _wordValue.value = word
    }

    fun goToNextTry() {
        _activeTry.value = _activeTry.value?.plus(1)
    }

    fun goToNextLetter() {
        _activeLetter.value = _activeLetter.value?.plus(1)
    }

    fun goToPreviousLetter() {
        if ((_activeLetter.value?:0) > 0) {
            _activeLetter.value = _activeLetter.value?.minus(1)
        }
    }

    fun addTry(word :String) {
        _passedTries.value?.add(word)
    }

    fun checkWord(word: String): Boolean {
        return loadWordManager.checkWordIsReal(word)
    }

}