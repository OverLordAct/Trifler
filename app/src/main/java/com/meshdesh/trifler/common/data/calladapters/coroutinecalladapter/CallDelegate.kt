package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import com.meshdesh.trifler.common.data.entity.Result
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<Success, Failure>(
    protected val proxy: Call<Success>
) : Call<Result<Success, Failure>> {

    override fun cancel() = proxy.cancel()

    override fun clone(): Call<Result<Success, Failure>> = cloneImpl()

    final override fun enqueue(callback: Callback<Result<Success, Failure>>) = enqueueImpl(callback)

    override fun execute(): Response<Result<Success, Failure>> = throw NotImplementedError()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    abstract fun cloneImpl(): Call<Result<Success, Failure>>

    abstract fun enqueueImpl(callback: Callback<Result<Success, Failure>>)
}
