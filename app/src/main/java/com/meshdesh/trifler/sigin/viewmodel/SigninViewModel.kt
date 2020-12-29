package com.meshdesh.trifler.sigin.viewmodel

import androidx.lifecycle.MutableLiveData

interface SigninViewModel {
    var signinStatusLiveData: MutableLiveData<SigninStatus>

    fun startSignin(email: String, password: String)

    sealed class SigninStatus {
        data class Success(val message: String) : SigninStatus()

        data class Failure(
            val message: String
        ) : SigninStatus()

        sealed class Blank : SigninStatus() {
            object Email : Blank()
            object Password : Blank()
        }

        object Loading : SigninStatus()
    }
}