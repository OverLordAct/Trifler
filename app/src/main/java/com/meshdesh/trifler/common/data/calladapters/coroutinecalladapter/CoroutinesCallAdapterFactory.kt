package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import com.meshdesh.trifler.common.data.entity.Result
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutinesCallAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        fun create(): CallAdapter.Factory {
            return CoroutinesCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Call::class.java -> {
                val containerType = getParameterUpperBound(0, returnType as ParameterizedType)
                check(containerType is ParameterizedType) { "Return type must be parameterized as Result<Success, Failure>" }

                val successType = getParameterUpperBound(0, containerType)
                val errorType = getParameterUpperBound(1, containerType)

                val errorConverter =
                    retrofit.nextResponseBodyConverter<Any>(null, errorType, annotations)

                ResultCallAdapter<Any, Any>(successType, errorConverter)
            }
            else -> null
        }
    }

    private class ResultCallAdapter<Success : Any, Failure : Any>(
        private val type: Type,
        private val errorConverter: Converter<ResponseBody, Failure>
    ) : CallAdapter<Success, Call<Result<Success, Failure>>> {
        override fun responseType() = type

        override fun adapt(call: Call<Success>): Call<Result<Success, Failure>> {
            return ResultCall(call, errorConverter, type)
        }
    }
}