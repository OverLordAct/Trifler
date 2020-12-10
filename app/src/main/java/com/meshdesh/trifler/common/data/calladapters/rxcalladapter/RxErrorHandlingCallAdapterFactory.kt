package com.meshdesh.trifler.common.data.calladapters.rxcalladapter

import com.meshdesh.trifler.common.exception.RetrofitException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    private val original = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapter(original.get(returnType, annotations, retrofit) ?: return null)
    }

    private class RxCallAdapter<R>(private val wrapped: CallAdapter<R, *>) :
        CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            return when (val result = wrapped.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext { throwable ->
                    Single.error(
                        asRetrofitException(throwable)
                    )
                }
                is Completable -> result.onErrorResumeNext { throwable ->
                    Completable.error(
                        asRetrofitException(throwable)
                    )
                }
                is Observable<*> -> result.onErrorResumeNext { throwable: Throwable ->
                    Observable.error(
                        asRetrofitException(throwable)
                    )
                }
                else -> RuntimeException("Runtime exception has occurred")
            }

        }

        fun asRetrofitException(throwable: Throwable): RetrofitException {
            return when (throwable) {
                is RetrofitException -> throwable

                is HttpException -> throwable.response()!!.let {
                    RetrofitException.httpError(
                        it.raw().request.url.toString(),
                        it,
                        throwable
                    )
                }

                is IOException -> RetrofitException.networkError(throwable)

                else -> RetrofitException.unexpectedError(throwable)
            }
        }
    }
}

class RxCallAdapter<R>(private val wrapped: CallAdapter<R, *>) :
    CallAdapter<R, Any> {

    override fun responseType(): Type {
        return wrapped.responseType()
    }

    override fun adapt(call: Call<R>): Any {
        return when (val result = wrapped.adapt(call)) {
            is Single<*> -> result.onErrorResumeNext { throwable ->
                Single.error(
                    asRetrofitException(throwable)
                )
            }
            is Completable -> result.onErrorResumeNext { throwable ->
                Completable.error(
                    asRetrofitException(throwable)
                )
            }
            is Observable<*> -> result.onErrorResumeNext { throwable: Throwable ->
                Observable.error(
                    asRetrofitException(throwable)
                )
            }
            else -> RuntimeException("Runtime exception has occurred")
        }

    }

    fun asRetrofitException(throwable: Throwable): RetrofitException {
        return when (throwable) {
            is RetrofitException -> throwable

            is HttpException -> throwable.response()!!.let {
                RetrofitException.httpError(
                    it.raw().request.url.toString(),
                    it,
                    throwable
                )
            }

            is IOException -> RetrofitException.networkError(throwable)

            else -> RetrofitException.unexpectedError(throwable)
        }
    }
}