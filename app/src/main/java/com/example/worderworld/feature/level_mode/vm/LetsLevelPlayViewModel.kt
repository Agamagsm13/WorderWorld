package com.agamatech.worderworld.feature.lets_play.vm

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.agamatech.worderworld.domain.manager.StoreItem
import com.agamatech.worderworld.domain.manager.BillingService
import com.agamatech.worderworld.feature.lets_play.usecase.GetLevelModeOpenUseCase
import com.agamatech.worderworld.feature.lets_play.usecase.GetMaxLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class LetsLevelPlayViewModel @Inject constructor(
    private val billing: BillingService,
    private val getMaxLevelUseCase: GetMaxLevelUseCase,
    private val getLevelModeOpenUseCase: GetLevelModeOpenUseCase,
): ViewModel() {

    val itemList = getPurchasesFlow()
        .catch { errorMessage.value = it.message }
        .asLiveData()

    val errorMessage = MutableLiveData<String?>()

    private val _maxLevel = MutableLiveData<Int>()
    val maxLevel: LiveData<Int> = _maxLevel

    private val _levelModeOpened = MutableLiveData<Boolean>()
    val levelModeOpened: LiveData<Boolean> = _levelModeOpened

    fun getMaxLevel() {
        _maxLevel.value = getMaxLevelUseCase()
    }

    fun getLevelModeOpen(): Boolean {
        val value = getLevelModeOpenUseCase()
        _levelModeOpened.value = value
        return value
    }

    fun isLevelModeOpen(): Boolean {
        return getLevelModeOpenUseCase()
    }

    fun launchItemPurchase(itemSku: StoreItem, activity: Activity) {
        billing.launchPurchaseFlow(activity, itemSku)
    }

    private fun getPurchasesFlow(): Flow<List<StoreItem>> {
        return billing.ballProducts
    }
}