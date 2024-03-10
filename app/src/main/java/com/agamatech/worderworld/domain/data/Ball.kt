package com.agamatech.worderworld.domain.data

enum class Trophy(val id: String, val title: String, val text: String) {
    Words10("words_10", "Newbie", "Guess 10 words"),
    Words50("words_50", "Master", "Guess 50 words"),
    Words100("words_100", "Guru", "Guess 100 words"),
    Looser("looser", "How do you do that?","Lose without guessing a single letter"),
    FirstTry("first_try", "Cheater", "Guess the word on the first try");

    companion object {
        fun findById(id: String): Trophy? {
            return values().firstOrNull { it.id == id }
        }
        fun allDefault() = values()
    }
}
