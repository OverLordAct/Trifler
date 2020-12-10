package com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.meshdesh.trifler.common.data.entity.Result
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

class ResultCall<Success: Any, Failure: Any>(
    private val backingCall: Call<Success>,
    private val errorConverter: Converter<ResponseBody, Failure>,
    private val successBodyType: Type
): CallDelegate<Success, Failure>(backingCall) {
    override fun cloneImpl(): Call<Result<Success, Failure>> = ResultCall(proxy.clone(), errorConverter, successBodyType)

    override fun enqueueImpl(callback: Callback<Result<Success, Failure>>) = backingCall.enqueue(object : Callback<Success> {
        override fun onResponse(call: Call<Success>, response: Response<Success>) {
            val networkResponse = ResponseHandler.handle(response, successBodyType, errorConverter)
            callback.onResponse(this@ResultCall, Response.success(networkResponse))
        }

        override fun onFailure(call: Call<Success>, t: Throwable) {
            val networkResponse = t.extractNetworkResponse<Success, Failure>(errorConverter)
            callback.onResponse(this@ResultCall, Response.success(networkResponse))
        }
    })
}