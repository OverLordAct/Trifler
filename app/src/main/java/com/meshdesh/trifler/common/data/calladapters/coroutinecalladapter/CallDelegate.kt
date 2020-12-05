package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<Tin, Tout>(
    protected val proxy: Call<Tin>
) : Call<Tout> {

    override fun cancel() = proxy.cancel()
    override fun clone(): Call<Tout> = cloneImpl()

    final override fun enqueue(callback: Callback<Tout>) = enqueueImpl(callback)

    override fun execute(): Response<Tout> = throw NotImplementedError()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    abstract fun cloneImpl(): Call<Tout>
    abstract fun enqueueImpl(callback: Callback<Tout>)
}