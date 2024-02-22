package com.example.worderworld.feature

import android.content.Context
import com.agamatech.worderworld.R
import com.agamatech.worderworld.domain.data.AppLocalStorage
import com.agamatech.worderworld.utils.getRawTextFile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadWordManager @Inject constructor(
    val localStorage: AppLocalStorage,
) {

    private var listWords = listOf<String>()
    private var words = mapOf<Int, List<String>>()

    fun loadWords(context: Context) {
        listWords = context.resources.getRawTextFile(R.raw.nouns).split("\n").map { it.trim().toUpperCase() }
        words = listWords.groupBy { it.length }
    }

    fun getRandomWord(length: Int): String {
        if (words[length]?.isNotEmpty() == true) {
            return words[length]?.randomOrNull()?.toUpperCase()?: "WHEEL"
        }
        return "WHEEL"
    }

    fun checkWordIsReal(word: String): Boolean {
        return listWords.contains(word)
    }

}