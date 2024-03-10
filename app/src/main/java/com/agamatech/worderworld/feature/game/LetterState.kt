package com.agamatech.worderworld.feature.game


enum class LetterState(val state: Int) {
    INPUT(0), RESULT_FALSE(1), RESULT_WRONG_PLACE(2), RESULT_OK(3);

    companion object {
        fun parse(state: Int): LetterState? {
            return values().firstOrNull { it.state == state }
        }
    }
}