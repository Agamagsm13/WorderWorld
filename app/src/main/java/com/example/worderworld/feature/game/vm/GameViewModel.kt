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
import com.agamatech.worderworld.utils.pxToDp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.sign

@HiltViewModel
class GameViewModel @Inject constructor(
    private val setBestScoreUseCase: SetBestScoreUseCase,
    private val getBestScoreUseCase: GetBestScoreUseCase,
    private val getSelectedBallUseCase: GetSelectedBallUseCase,
    private val getIntersCountUseCase: GetIntersCountUseCase,
    private val setIntersCountUseCase: SetIntersCountUseCase
): ViewModel() {
    private val G = 2f
    private val _coordinates = MutableLiveData<Pair<Float, Float>>()
    val coordinates: LiveData<Pair<Float, Float>> = _coordinates

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val _leftEdgeTouched = MutableLiveData<Boolean>()
    val leftEdgeTouched: LiveData<Boolean> = _leftEdgeTouched

    private val _rightEdgeTouched = MutableLiveData<Boolean>()
    val rightEdgeTouched: LiveData<Boolean> = _rightEdgeTouched

    private val _gameFailed = MutableLiveData<Boolean>()
    val gameFailed: LiveData<Boolean> = _gameFailed

    private val _gameRunning = MutableLiveData<Boolean>()
    val gameRunning: LiveData<Boolean> = _gameRunning

    private val _bumpCount = MutableLiveData<Int>()
    val bumpCount: LiveData<Int> = _bumpCount

    private val _direction = MutableLiveData<Int>(0)
    val direction: LiveData<Int> = _direction

    private val _selectedBall = MutableLiveData<String>()
    val selectedBall: LiveData<String> get() = _selectedBall

    private var startCoordinates: Pair<Float, Float> = Pair(0f, 0f)
    private var screenSize: Pair<Float, Float> = Pair(0f, 0f)
    private var speed: Pair<Float, Float> = Pair(0f, 0f)
    private val baseSpeedX = -25f
    val radiusInPx = 50f.dpToPx()
    private val doubleRadiusInPx = 100f.dpToPx()
    private val tripleRadiusInPx = 150f.dpToPx()

    private val f = flow<Double> {  }

    private fun setSpeed(value: Pair<Float, Float>) {
        speed = value
    }

    fun setCoordinates(value: Pair<Float, Float>) {
        _coordinates.value = value
    }

    fun setStartCoordinates(value: Pair<Float, Float>) {
        startCoordinates = value
    }

    fun setScreenSize(value: Pair<Float, Float>) {
        screenSize = value
    }

    private fun plusScore() {
        _score.value = (score.value?: 0) + 1
    }

    fun minusScore() {
        _score.value = (score.value?: 0) - (_bumpCount.value?: 0)
    }

    fun getIntersCount(): Int {
        return getIntersCountUseCase()
    }

    fun setIntersCount() {
        setIntersCountUseCase()
    }

    fun hitBall(x: Float) {
        viewModelScope.launch {
            if (gameRunning.value != true) {
                _gameRunning.value = true
                startGame()
            }
            plusScore()
            val xLength = 50 - x.pxToDp()
            var speedX = -baseSpeedX * xLength
            speedX = speedX.sign * abs(speedX).coerceIn(0f, 5f)
            setSpeed(Pair(speedX, -45f))
            withContext(Dispatchers.Main) {
                setDirection()
            }
        }
    }

    private fun startGame() {
        _coordinates.value = startCoordinates
        _bumpCount.value = 0
        viewModelScope.launch {
            while (gameRunning.value == true) {
                val newYSpeed = calculateNewYSpeed()
                val coors =  Pair(
                    (coordinates.value?.first ?: 0f) + ((speed.first)),
                    (coordinates.value?.second ?: 0f) + newYSpeed)
                if (coors.second >= ((screenSize.second) + (tripleRadiusInPx))) {
                    withContext(Dispatchers.Main) {
                        setIntersCountUseCase()
                        _gameRunning.value = false
                        _gameFailed.postValue(true)
                    }
                    val curScore = score.value ?: 0
                    if (curScore > getBestScoreUseCase()) {
                        withContext(Dispatchers.Main) {
                            setBestScoreUseCase(curScore)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        _score.value = 0
                        _coordinates.value = startCoordinates
                        _direction.value = 0
                    }
                } else if (coors.first <= 2f) {
                    speed = Pair(-(speed.first), newYSpeed)
                    withContext(Dispatchers.Main) {
                        setDirection()
                        _coordinates.value = Pair(1f, coors.second)
                        _bumpCount.value = (_bumpCount.value ?: 0) + 1
                        _leftEdgeTouched.postValue(true)
                    }
                } else if ((coors.first + doubleRadiusInPx) >= (screenSize.first)) {
                    speed = Pair(-(speed.first), newYSpeed)
                    withContext(Dispatchers.Main) {
                        setDirection()
                        _coordinates.value = Pair(
                            (screenSize.first) - (doubleRadiusInPx - 1),
                            (coors.second)
                        )
                        _bumpCount.value = (_bumpCount.value ?: 0) + 1
                        _rightEdgeTouched.postValue(true)
                    }
                } else {
                    speed = Pair(speed.first, newYSpeed)
                    withContext(Dispatchers.Main) {
                        setDirection()
                        _coordinates.value = coors
                    }
                }
                delay(16)
            }
        }
    }
    fun getSelectedBall() {
        _selectedBall.value = getSelectedBallUseCase()
    }

    private fun calculateNewYSpeed(): Float {
        return speed.second + G
    }

    private fun setDirection() {
        if ((speed.first?: 0) == 0) {
            _direction.value = 0
        } else if (speed.first < 0 && (direction.value?: 0) >= 0) {
            _direction.value = -1
        } else if (speed.first > 0 && (direction.value?: 0) <= 0) {
            _direction.value = 1
        }
    }
}