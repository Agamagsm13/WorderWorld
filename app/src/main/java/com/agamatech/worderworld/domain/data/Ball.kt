package com.agamatech.worderworld.domain.data

enum class Trophy(val id: String, val text: String) {
    Words10("words_10", "Guess 10 words"),
    Words50("words_50", "Guess 50 words"),
    Words100("words_100", "Guess 100 words"), ;

    companion object {
        fun findById(id: String): Trophy? {
            return values().firstOrNull { it.id == id }
        }
        fun allDefault() = values()
    }
}
