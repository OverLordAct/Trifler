package com.meshdesh.trifler.sigin.viewmodel

import androidx.lifecycle.MutableLiveData

interface LoginViewModel {
    var loginStatusLiveData: MutableLiveData<LoginStatus>

    sealed class LoginStatus {
        object Success : LoginStatus()

        data class Failure(
            val message: String
        ) : LoginStatus()

        sealed class Blank : LoginStatus() {
            data class Email(val message: String) : Blank()
            data class Password(val message: String) : Blank()
            data class Both(val email: String, val password: String) : Blank()
        }

        object Loading : LoginStatus()
    }
}