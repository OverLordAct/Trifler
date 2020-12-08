package com.meshdesh.trifler.common.data.entity

import java.io.IOException

sealed class Result<out Success, out Error> {
    data class Success<out Success>(val data: Success?) : Result<Success, Nothing>()

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    data class ServerError<Error : Any>(val body: Error?) : Result<Nothing, Error>()

    /**
     * A request that didn't result in a response.
     */
    data class NetworkError(val error: IOException) : Result<Nothing, Nothing>()

    /**
     * A request that resulted in an error different from an IO or Server error.
     *
     * An example of such an error is JSON parsing exception thrown by a serialization library.
     */
    data class UnknownError(val error: Throwable) : Result<Nothing, Nothing>()
}
