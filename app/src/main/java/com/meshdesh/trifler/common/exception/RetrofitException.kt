package com.meshdesh.trifler.common.exception

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * The request URL which produced the error.
 */

/**
 * Response object containing status code, headers, body, etc.
 */

/**
 * The event kind which triggered this error.
 */

class RetrofitException private constructor(
    message: String?,
    private val url: String?,
    private val response: Response<*>?,
    private val kind: Kind,
    exception: Throwable
) : RuntimeException(message, exception) {

    override fun toString(): String {
        return super.toString() + " : " + kind + " : " + url + " : " + response?.errorBody()
            ?.string()
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,

        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {
        fun httpError(
            url: String,
            response: Response<*>,
            httpException: HttpException
        ): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, httpException)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.UNEXPECTED, exception)
        }
    }
}