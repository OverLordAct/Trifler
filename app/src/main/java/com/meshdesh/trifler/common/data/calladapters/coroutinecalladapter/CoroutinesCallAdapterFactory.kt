package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.meshdesh.trifler.common.data.entity.Result

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
    ): CallAdapter<*, *> = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> throw Exception("Response type must be parameterized as Result<T>")
            }
        }
        else -> throw Exception("Return type must be parameterized as Call<T>")
    }

    private class ResultAdapter(
        private val type: Type
    ) : CallAdapter<Type, Call<Result<Type>>> {
        override fun responseType(): Type = type

        override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
    }
}
