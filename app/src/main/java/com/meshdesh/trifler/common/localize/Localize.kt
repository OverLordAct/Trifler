package com.meshdesh.trifler.common.localize

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class Localize @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun localize(@StringRes resId: Int) = context.getString(resId)
}