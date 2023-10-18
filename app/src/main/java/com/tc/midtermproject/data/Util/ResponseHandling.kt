package com.tc.midtermproject.data.Util

sealed class ResponseHandling<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): ResponseHandling<T>(data)

    class Error<T>(message: String, data: T? = null): ResponseHandling<T>(data, message)

    class Loading<T>(data: T? = null): ResponseHandling<T>(data)
}
