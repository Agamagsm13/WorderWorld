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

    var words = listOf<String>()

    fun loadWords(context: Context) {
        words = context.resources.getRawTextFile(R.raw.nouns).split("\n").map { it.trim() }
    }

    fun getRandomWord() = if (words.isNotEmpty()) words.random() else "hello"

}