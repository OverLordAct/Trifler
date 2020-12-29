package com.meshdesh.trifler.util

import com.meshdesh.trifler.common.data.tag.AuthenticatedTag
import okhttp3.Response
import retrofit2.Invocation

private fun Response.checkAuthenticatedTag(): Boolean {
    request.tag(Invocation::class.java)?.let {
        it.method().getAnnotation(AuthenticatedTag::class.java)?.let {
            return true
        }
    }
    return false
}
