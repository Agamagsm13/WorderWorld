package com.agamatech.worderworld.feature.game.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agamatech.worderworld.domain.data.Trophy
import com.agamatech.worderworld.feature.game.usecase.GetIntersCountUseCase
import com.agamatech.worderworld.feature.game.usecase.SetIntersCountUseCase
import com.agamatech.worderworld.feature.level_game.usecase.SetMaxLevelUseCase
import com.agamatech.worderworld.feature.game.usecase.SetWordsGuessedUseCase
import com.agamatech.worderworld.feature.level_mode.usecase.GetMaxLevelUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetWordsGuessedUseCase
import com.agamatech.worderworld.feature.LoadWordManager
import com.agamatech.worderworld.feature.trophies.usecase.AddIfPossibleTrophyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelGameViewModel @Inject constructor(
    private val setWordsGuessedUseCase: SetWordsGuessedUseCase,
    private val getWordsGuessedUseCase: GetWordsGuessedUseCase,
    private val setMaxLevelUseCase: SetMaxLevelUseCase,
    private val getMaxLevelUseCase: GetMaxLevelUseCase,
    private val getIntersCountUseCase: GetIntersCountUseCase,
    private val setIntersCountUseCase: SetIntersCountUseCase,
    private val addIfPossibleTrophyUseCase: AddIfPossibleTrophyUseCase,
    private val loadWordManager: LoadWordManager,
): ViewModel() {

    private val _passedTries = MutableLiveData<MutableList<String>>()
    val passedTries: LiveData<MutableList<String>> = _passedTries

    private val _wordValue = MutableLiveData<String>()
    val wordValue: LiveData<String> = _wordValue

    private val _firstGame = MutableLiveData<Boolean>(true)
        val firstGame: LiveData<Boolean> = _firstGame

    private val _activeTry = MutableLiveData<Int>()
    val activeTry: LiveData<Int> = _activeTry

    private val _activeLetter = MutableLiveData<Int>()
    val activeLetter: LiveData<Int> = _activeLetter

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val _currentLevel = MutableLiveData<Int>()
    val currentLevel: LiveData<Int> = _currentLevel


    private val _badLetters = MutableLiveData<List<String>>()
    val badLetters: LiveData<List<String>> = _badLetters

    private val _goodLetters = MutableLiveData<List<String>>()
    val goodLetters: LiveData<List<String>> = _goodLetters

    init {
        _currentLevel.value = 1
        _activeTry.value = 0
        _activeLetter.value = 0
        _passedTries.value = mutableListOf()
        _badLetters.value = mutableListOf()
        val guessedWords = getWordsGuessedUseCase()
        if (guessedWords >= 10) {
            addIfPossibleTrophyUseCase(Trophy.Words10.id)
        }
        if (guessedWords >= 50) {
            addIfPossibleTrophyUseCase(Trophy.Words50.id)
        }
        if (guessedWords >= 100) {
            addIfPossibleTrophyUseCase(Trophy.Words100.id)
        }
    }

    fun setWord(word: String) {
        _wordValue.value = word
    }

    fun goToNextTry() {
        _activeTry.value = _activeTry.value?.plus(1)
        _activeLetter.value = 0
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
        goToNextTry()
    }

    fun addBadLetter(value: String) {
        if (_badLetters.value?.contains(value) != true) {
            _badLetters.value = (_badLetters.value?: listOf()) + listOf(value)
        }
    }

    fun addGoodLetter(value: String) {
        if (_goodLetters.value?.contains(value) != true) {
            _goodLetters.value = (_goodLetters.value?: listOf()) + listOf(value)
        }
    }

    fun resetGame(length: Int, win: Boolean) {
        if (win) {
            _currentLevel.value = (_currentLevel.value?:1) + 1
            if ((_currentLevel.value?: 1) > getMaxLevelUseCase()) {
                setMaxLevelUseCase(_currentLevel.value?: 1)
            }
            if ((_currentLevel.value?: 1) >= 5) {
                addIfPossibleTrophyUseCase(Trophy.Level5.id)
            }
            if ((_currentLevel.value?: 1) >= 10) {
                addIfPossibleTrophyUseCase(Trophy.Level10.id)
            }
            if ((_currentLevel.value?: 1) >= 20) {
                addIfPossibleTrophyUseCase(Trophy.Level20.id)
            }
        } else {
            _currentLevel.value = 1
        }
        _passedTries.value = mutableListOf()
        _badLetters.value = mutableListOf()
        _goodLetters.value = mutableListOf()
        _activeTry.value = 0
        _activeLetter.value = 0
        _firstGame.value = false
        _wordValue.value = loadWordManager.getRandomWord(length)
    }

    fun checkWord(word: String): Boolean {
        return loadWordManager.checkWordIsReal(word)
    }

    fun addWordToGuessed() {
        if (activeTry.value == 0) {
            addIfPossibleTrophyUseCase(Trophy.FirstTry.id)
        }
        val guessedWords = getWordsGuessedUseCase() + 1
        setWordsGuessedUseCase(guessedWords)
        if (guessedWords >= 10) {
            addIfPossibleTrophyUseCase(Trophy.Words10.id)
        }
        if (guessedWords >= 50) {
            addIfPossibleTrophyUseCase(Trophy.Words50.id)
        }
        if (guessedWords >= 100) {
            addIfPossibleTrophyUseCase(Trophy.Words100.id)
        }
    }

    fun checkLooserTrophy() {
        if (goodLetters.value.isNullOrEmpty()) {
            addIfPossibleTrophyUseCase(Trophy.Looser.id)
        }
    }

    fun getIntersCount() = getIntersCountUseCase()

    fun setIntersCount() = setIntersCountUseCase()

}