package com.core.network.network

sealed class NetworkResult<out T : Any, out E : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T, Nothing>()
    class Error<E : Any>(val code: Int, val message: String?, val errorData: E? = null) : NetworkResult<Nothing, E>()
    class Exception(val e: Throwable, val code: Int? = null) : NetworkResult<Nothing, Nothing>()
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onSuccess(
    executable: suspend (T) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(data)
    }
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onError(
    executable: suspend (code: Int, message: String?, errorData: E?) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Error<E>) {
        executable(code, message, errorData)
    }
}

suspend fun <T : Any, E : Any> NetworkResult<T, E>.onException(
    executable: suspend (e: Throwable, code: Int?) -> Unit,
): NetworkResult<T, E> = apply {
    if (this is NetworkResult.Exception) {
        executable(e, code)
    }
}
