package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import com.meshdesh.trifler.common.data.entity.Result
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.lang.reflect.Type

internal object ResponseHandler {

    /**
     * Converts the given [response] to a subclass of [NetworkResponse] based on different conditions.
     *
     * If the server response is successful with:
     * => a non-empty body -> NetworkResponse.Success<S, E>
     * => an empty body (and [successBodyType] is Unit) -> NetworkResponse.Success<Unit, E>
     * => an empty body (and [successBodyType] is not Unit) -> NetworkResponse.ServerError<E>
     *
     * @param response Retrofit's response object supplied to the call adapter
     * @param successBodyType A [Type] representing the success body
     * @param errorConverter A retrofit converter to convert the error body into the error response type (E)
     * @param S The success body type generic parameter
     * @param E The error body type generic parameter
     */
    fun <S : Any, E : Any> handle(
        response: Response<S>,
        successBodyType: Type,
        errorConverter: Converter<ResponseBody, E>
    ): Result<S, E> {
        val body = response.body()
        val errorBody = response.errorBody()

        return if (response.isSuccessful) {
            if (body != null) {
                Result.Success(body)
            } else {
                // Special case: If the response is successful and the body is null, return a successful response
                // if the service method declares the success body type as Unit. Otherwise, return a server error
                if (successBodyType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    Result.Success(Unit) as Result<S, E>
                } else {
                    Result.ServerError(null)
                }
            }
        } else {
            val result: Result<S, E> = try {
                val convertedBody = errorConverter.convert(errorBody)
                Result.ServerError(convertedBody)
            } catch (ex: Exception) {
                Result.UnknownError(ex)
            }
            result
        }
    }

}