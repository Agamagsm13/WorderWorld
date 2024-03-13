package com.agamatech.worderworld.domain.data

import android.content.SharedPreferences

class AppLocalStorage(private val prefs: SharedPreferences) {

    object Keys {
        const val wordsGuessed = "wordsGuessedKey"
        const val wordsGuessedRow = "wordsGuessedRowKey"
        const val intersCount = "intersCountKey"
        const val allWordsEnabled = "allWordsEnabledKey"
        const val maxLevel = "maxLevelKey"
        const val trophies = "trophiesKey"
        const val levelModeEnabled = "levelModeEnabledKey"
    }

    var wordsGuessed: Int
        get() = prefs.getInt(Keys.wordsGuessed, 0)
        set(value) {
            prefs.edit().putInt(Keys.wordsGuessed, value).apply()
        }
    var wordsGuessedRow: Int
        get() = prefs.getInt(Keys.wordsGuessedRow, 0)
        set(value) {
            prefs.edit().putInt(Keys.wordsGuessedRow, value).apply()
        }
    var maxLevel: Int
        get() = prefs.getInt(Keys.maxLevel, 1)
        set(value) {
            prefs.edit().putInt(Keys.maxLevel, value).apply()
        }
    var trophies: String?
        get() = prefs.getString(Keys.trophies, "")
        set(value) {
            prefs.edit().putString(Keys.trophies, value).apply()
        }
    var allWordsOpen: Boolean
        get() = prefs.getBoolean(Keys.allWordsEnabled, false)
        set(value) {
            prefs.edit().putBoolean(Keys.allWordsEnabled, value).apply()
        }
    var levelModeOpen: Boolean
        get() = prefs.getBoolean(Keys.levelModeEnabled, true)
        set(value) {
            prefs.edit().putBoolean(Keys.levelModeEnabled, value).apply()
        }
    var intersCount: Int
        get() = prefs.getInt(Keys.intersCount, 0)
        set(value) {
            prefs.edit().putInt(Keys.intersCount, value).apply()
        }
}