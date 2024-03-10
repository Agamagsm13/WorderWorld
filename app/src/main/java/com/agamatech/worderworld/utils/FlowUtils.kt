package com.agamatech.worderworld.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.shareIn

fun <T> Flow<T>.toSingleLazyFlow(): SharedFlow<T> {
    return conflate().shareIn(GlobalScope, SharingStarted.Lazily, 1)
}

fun <T> Flow<T>.toSingleEagerFlow(): SharedFlow<T> {
    return conflate().shareIn(GlobalScope, SharingStarted.Eagerly, 1)
}