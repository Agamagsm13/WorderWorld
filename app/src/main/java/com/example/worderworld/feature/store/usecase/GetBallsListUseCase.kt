package com.agamatech.worderworld.feature.store.usecase

import com.agamatech.worderworld.domain.data.AppLocalStorage
import com.agamatech.worderworld.domain.data.Ball
import com.agamatech.worderworld.domain.manager.BallStoreItem
import javax.inject.Inject

class GetBallsListUseCase @Inject constructor(
    private val localStorage: AppLocalStorage
) {
    operator fun invoke(purchaseBalls: List<BallStoreItem>): List<Ball> {
        val ballsIds: List<String> = listOf("ball_1", "ball_2").union(purchaseBalls.map { it.ballItem.playProductId }).toList()
        val balls: MutableList<Ball> = mutableListOf()
        val selectedBall = localStorage.selectedBall?: "ball_1"
        val enableIds = localStorage.enabledBalls?.split(" ")?: listOf()
        ballsIds.forEach {ballId ->
            balls.add(Ball(id = ballId, enable = enableIds.contains(ballId), selected = selectedBall == ballId, priceString = purchaseBalls.firstOrNull{it.ballItem.playProductId == ballId}?.priceString?: "", skuItem = purchaseBalls.firstOrNull{it.ballItem.playProductId == ballId}))
        }
        return balls
    }
}