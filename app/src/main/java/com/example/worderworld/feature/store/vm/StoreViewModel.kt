package com.agamatech.worderworld.feature.store.vm

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.agamatech.worderworld.domain.data.Ball
import com.agamatech.worderworld.domain.manager.BallStoreItem
import com.agamatech.worderworld.domain.manager.BillingService
import com.agamatech.worderworld.feature.store.usecase.GetBallsListUseCase
import com.agamatech.worderworld.feature.store.usecase.SelectBallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val billing: BillingService,
    private val getBallsListUseCase: GetBallsListUseCase,
    private val selectBallUseCase: SelectBallUseCase
): ViewModel() {
    private val _ballsList = MutableLiveData<List<Ball>>()
    val ballsList: LiveData<List<Ball>> get() = _ballsList


    val itemList = getPurchasesFlow()
        .catch { errorMessage.value = it.message }
        .asLiveData()


    val errorMessage = MutableLiveData<String?>()

    fun launchItemPurchase(itemSku: BallStoreItem, activity: Activity) {
        billing.launchPurchaseFlow(activity, itemSku)
    }
    fun initBalls(purchaseBalls: List<BallStoreItem>) {
        _ballsList.postValue(getBallsListUseCase(purchaseBalls))
    }

    fun selectBall(id: String) {
        selectBallUseCase(id)
        initBalls(itemList.value?: listOf())
    }

    fun updateList() {
        initBalls(itemList.value?: listOf())
    }

    private fun getPurchasesFlow(): Flow<List<BallStoreItem>> {
        return billing.ballProducts
    }
}