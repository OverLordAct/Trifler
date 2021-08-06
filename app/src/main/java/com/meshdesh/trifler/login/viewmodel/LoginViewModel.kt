package com.meshdesh.trifler.login.viewmodel

import androidx.lifecycle.MutableLiveData

interface LoginViewModel {
    var loginStatusLiveData: MutableLiveData<LoginStatus>

    fun startLogin(email: String, password: String)

    sealed class LoginStatus {
        data class Success(val message: String) : LoginStatus()

        data class Failure(
            val message: String
        ) : LoginStatus()

        sealed class Blank : LoginStatus() {
            object Email : Blank()
            object Password : Blank()
        }

        object Loading : LoginStatus()
    }
}