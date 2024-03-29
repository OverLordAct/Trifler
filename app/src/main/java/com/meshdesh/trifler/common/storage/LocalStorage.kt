package com.meshdesh.trifler.common.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    companion object {
        private const val PREF_NAME = "PREF_NAME"
    }

    private var sharedPreference: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    operator fun set(key: KEY, value: Any) {
        sharedPreference.edit()
            .putString(key.toString(), gson.toJson(value))
            .apply()
    }

    inline operator fun <reified T : Any> get(key: KEY, defaultValue: T? = null): T? {
        val type = object : TypeToken<T>() {}.type
        return internalGet(key, type, defaultValue)
    }

    @PublishedApi
    internal fun <T : Any> internalGet(key: KEY, type: Type, defaultValue: T? = null): T? {
        return try {
            return gson.fromJson(sharedPreference.getString(key.toString(), null), type)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun delete(key: KEY) {
        sharedPreference.edit()
            .remove(key.toString())
            .apply()
    }
}

enum class KEY {
    ONBOARDING,
    USERNAME,
    PHONE,
    ACCESS_TOKEN,
    REFRESH_TOKEN
}