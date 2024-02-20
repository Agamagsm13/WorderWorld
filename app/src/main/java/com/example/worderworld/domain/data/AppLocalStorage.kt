package com.agamatech.worderworld.domain.data

import android.content.SharedPreferences

class AppLocalStorage(private val prefs: SharedPreferences) {

    object Keys {
        const val wordsGuessed = "wordsGuessedKey"
        const val enabledBalls = "enabledBallsKey"
        const val selectedBall = "selectedBallKey"
        const val intersCount = "intersCountKey"
    }

    var wordsGuessed: Int
        get() = prefs.getInt(Keys.wordsGuessed, 0)
        set(value) {
            prefs.edit().putInt(Keys.wordsGuessed, value).apply()
        }

    var enabledBalls: String?
        get() = prefs.getString(Keys.enabledBalls, " ball_1 ball_2")
        set(value) {
            prefs.edit().putString(Keys.enabledBalls, value).apply()
        }

    var intersCount: Int
        get() = prefs.getInt(Keys.intersCount, 0)
        set(value) {
            prefs.edit().putInt(Keys.intersCount, value).apply()
        }

    var selectedBall: String?
        get() = prefs.getString(Keys.selectedBall, "ball_1")
        set(value) {
            prefs.edit().putString(Keys.selectedBall, value).apply()
        }
}