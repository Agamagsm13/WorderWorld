package com.agamatech.worderworld.feature.daily.vm

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.agamatech.worderworld.domain.manager.StoreItem
import com.agamatech.worderworld.domain.manager.BillingService
import com.agamatech.worderworld.feature.daily.usecase.GetCurrentDateUseCase
import com.agamatech.worderworld.feature.daily.usecase.GetWordsGuessedRowUseCase
import com.agamatech.worderworld.feature.game.usecase.SetAllWordsOpenUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetAllWordsOpenUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetWordsGuessedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LetsPlayDailyViewModel @Inject constructor(
    private val getWordsGuessedRowUseCase: GetWordsGuessedRowUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase,
): ViewModel() {

    private val _wordsGuessedRow = MutableLiveData<Int>()
    val wordsGuessedRow: LiveData<Int> = _wordsGuessedRow

    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate

    fun getWordsGuessed() {
        _wordsGuessedRow.value = getWordsGuessedRowUseCase()
    }

    fun getDate() {
        viewModelScope.launch {
            val date = getCurrentDateUseCase()
            _currentDate.value = date
        }
    }
}