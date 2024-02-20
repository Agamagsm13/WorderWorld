package com.agamatech.worderworld.feature.store.vm

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.agamatech.worderworld.domain.data.Ball
import com.agamatech.worderworld.domain.manager.StoreItem
import com.agamatech.worderworld.domain.manager.BillingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val billing: BillingService,
): ViewModel() {
    private val _ballsList = MutableLiveData<List<Ball>>()
    val ballsList: LiveData<List<Ball>> get() = _ballsList


    val itemList = getPurchasesFlow()
        .catch { errorMessage.value = it.message }
        .asLiveData()


    val errorMessage = MutableLiveData<String?>()

    fun launchItemPurchase(itemSku: StoreItem, activity: Activity) {
        billing.launchPurchaseFlow(activity, itemSku)
    }


    private fun getPurchasesFlow(): Flow<List<StoreItem>> {
        return billing.ballProducts
    }
}