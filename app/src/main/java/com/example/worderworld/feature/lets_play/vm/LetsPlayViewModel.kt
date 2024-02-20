package com.agamatech.worderworld.feature.lets_play.vm

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.agamatech.worderworld.domain.manager.StoreItem
import com.agamatech.worderworld.domain.manager.BillingService
import com.agamatech.worderworld.feature.game.usecase.SetAllWordsOpenUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetAllWordsOpenUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetWordsGuessedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class LetsPlayViewModel @Inject constructor(
    private val billing: BillingService,
    private val getWordsGuessedUseCase: GetWordsGuessedUseCase,
    private val getAllWordsOpenUseCase: GetAllWordsOpenUseCase,
    private val setAllWordsOpenUseCase: SetAllWordsOpenUseCase,
): ViewModel() {

    val itemList = getPurchasesFlow()
        .catch { errorMessage.value = it.message }
        .asLiveData()

    val errorMessage = MutableLiveData<String?>()

    private val _letterCount = MutableLiveData<Int>()
    val letterCount: LiveData<Int> get() = _letterCount

    private val _wordsGuessed = MutableLiveData<Int>()
    val wordsGuessed: LiveData<Int> = _wordsGuessed

    private val _allOpened = MutableLiveData<Boolean>()
    val allOpened: LiveData<Boolean> = _allOpened

    fun getWordsGuessed() {
        _wordsGuessed.value = getWordsGuessedUseCase()
    }

    fun addLetterCount() {
        _letterCount.value = (_letterCount.value?: 0) + 1
    }

    fun minusLetterCount() {
        _letterCount.value = (_letterCount.value?: 0) - 1
    }

    fun getAllWordsOpen(): Boolean {
        val value = getAllWordsOpenUseCase()
        _allOpened.value = value
        return value
    }

    fun setLetterCount(value: Int) {
        _letterCount.value = value
    }

    fun launchItemPurchase(itemSku: StoreItem, activity: Activity) {
        billing.launchPurchaseFlow(activity, itemSku)
    }

    private fun getPurchasesFlow(): Flow<List<StoreItem>> {
        return billing.ballProducts
    }
}