package com.prof18.filmatik.domain

/**
 * A wrapper to contain successful and failure states
 */
sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val throwable: Throwable) : DataResult<Nothing>()
}
