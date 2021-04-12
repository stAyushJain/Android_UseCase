package com.target.targetcasestudy.util

sealed class DealResult<T> {
    data class Success<T>(val data: T?): DealResult<T>()
    data class Error<T>(val message: String?): DealResult<T>()
}