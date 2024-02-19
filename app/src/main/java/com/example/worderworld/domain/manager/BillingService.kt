package com.agamatech.worderworld.domain.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.agamatech.worderworld.feature.store.usecase.AddEnabledBallUseCase
import com.agamatech.worderworld.utils.toSingleEagerFlow
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.Purchase.PurchaseState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BillingService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addEnabledBallUseCase: AddEnabledBallUseCase
) : PurchasesUpdatedListener {


    val billingConnectedFlow = MutableStateFlow(false)
    private val storage = context.getSharedPreferences("BillingService", Context.MODE_PRIVATE)

    var productsFlow = flow {
        while (true) {
            if (billingConnectedFlow.first()) {
                emit(queryProductsDetails(BallProduct.values().asList()))
                break
            }
        }
    }.toSingleEagerFlow()


    val ballProducts = productsFlow
        .transform {
            val data = BallProduct.allDefault().mapNotNull { ballPurchase ->
                val product = it.firstOrNull { p -> p.productId == ballPurchase.playProductId }
                if (product != null) {
                    BallStoreItem(
                        ballPurchase,
                        product
                    )
                } else {
                    null
                }
            }
            emit(data)
        }.toSingleEagerFlow()

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this@BillingService)
        .enablePendingPurchases()
        .build()

    fun connect() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                val connected = billingResult.responseCode == BillingResponseCode.OK
                billingConnectedFlow.tryEmit(connected)
                restorePurchases()
            }

            override fun onBillingServiceDisconnected() {
                billingConnectedFlow.tryEmit(false)
            }
        })
    }

    fun restorePurchases() {
        if (isConnected()) {

        }
    }

    private fun isConnected(): Boolean = billingConnectedFlow.value

    private suspend fun queryProductsDetails(products: List<ProductIdType>): List<ProductDetails> {
        val pList = products.map {
            QueryProductDetailsParams.Product.newBuilder().setProductId(it.playProductId).setProductType(it.playProductType).build()
        }
        val qParams = QueryProductDetailsParams.newBuilder().setProductList(pList).build()
        billingClient.queryProductDetails(qParams).let { result ->
            if (result.billingResult.responseCode == BillingResponseCode.OK) {
                return result.productDetailsList ?: emptyList()
            }
        }
        return emptyList()
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
            purchases.forEach { handlePurchase(it) }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == PurchaseState.PURCHASED) {

            BallProduct.findById(purchase.products[0])?.let { ballPurchase ->
                billingClient.let {
                    val consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    it.consumeAsync(consumeParams) { result: BillingResult, _: String ->
                        if (result.responseCode == BillingResponseCode.OK) {
                            addEnabledBallUseCase(ballPurchase.playProductId)
                            val intent = Intent()
                            intent.action = "com.agamatech.worderworld.purchase"
                            context.sendBroadcast(intent)
                        }
                    }
                }
            }
        }
    }





    fun launchPurchaseFlow(activity: Activity, item: BallStoreItem) {
        val flowParams = buildFlowParams(item.productDetails)
        billingClient.launchBillingFlow(activity, flowParams)
    }

    fun launchPurchaseFlow(activity: Activity, product: ProductDetails) {
        val flowParams = buildFlowParams(product)
        billingClient.launchBillingFlow(activity, flowParams)
    }

    private fun buildFlowParams(product: ProductDetails, offerToken: String? = null): BillingFlowParams {
        val flowProductDetails = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(product)
        if (offerToken != null) {
            flowProductDetails.setOfferToken(offerToken)
        }

        return BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(flowProductDetails.build()))
            .build()
    }
}

data class BallStoreItem(
    val ballItem: BallProduct,
    val productDetails: ProductDetails,
) {
    val priceString = productDetails.oneTimePurchaseOfferDetails?.formattedPrice ?: ""
    val priceDecimal = (productDetails.oneTimePurchaseOfferDetails?.priceAmountMicros ?: 0) / 1e6F
}

interface ProductIdType {
    val playProductId: String
    val playProductType: String
}

enum class BallProduct(override val playProductId: String) : ProductIdType {
    Ball17("ball_17"),
    Ball18("ball_18"),
    Ball19("ball_19_1"),
    Ball20("ball_20"),
    Ball5("ball_5"),
    Ball9("ball_9_1"),
    Ball10("ball_10"),
    Ball11("ball_11_1"),
    Ball12("ball_12"),
    Ball13("ball_13"),
    Ball14("ball_14"),
    Ball15("ball_15"),
    Ball16("ball_16"),
    Ball6("ball_6"),
    Ball7("ball_7"),
    Ball8("ball_8");

    override val playProductType = BillingClient.ProductType.INAPP

    companion object {
        fun findById(id: String): BallProduct? {
            return values().firstOrNull { it.playProductId == id }
        }
        fun allDefault() = values()
    }
}