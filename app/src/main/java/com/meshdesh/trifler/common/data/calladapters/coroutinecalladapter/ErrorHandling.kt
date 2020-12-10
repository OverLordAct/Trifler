package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import com.meshdesh.trifler.common.data.entity.Result
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException

internal fun <E : Any> HttpException.extractFromHttpException(
    errorConverter: Converter<ResponseBody, E>
): Result.ServerError<E> {
    val error = response()?.errorBody()
    val errorBody = when {
        error == null -> null // No error content available
        error.contentLength() == 0L -> null // Error content is empty
        else -> try {
            // There is error content present, so we should try to extract it
            errorConverter.convert(error)
        } catch (e: Exception) {
            // If unable to extract content, return with a null body and don't parse further
            return Result.ServerError(null)
        }
    }
    return Result.ServerError(errorBody)
}

internal fun <S : Any, E : Any> Throwable.extractNetworkResponse(
    errorConverter: Converter<ResponseBody, E>
): Result<S, E> {
    return when (this) {
        is IOException -> Result.NetworkError(this)
        is HttpException -> extractFromHttpException(errorConverter)
        else -> Result.UnknownError(this)
    }
}