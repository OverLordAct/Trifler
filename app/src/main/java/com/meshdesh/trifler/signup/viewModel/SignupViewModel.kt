package com.meshdesh.trifler.signup.viewModel

import androidx.lifecycle.LiveData

interface SignupViewModel {

    val signupStatusLiveData: LiveData<SignupStatus>

    fun signup(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        conditionCheck: Boolean
    )

    sealed class SignupStatus {
        object Loading : SignupStatus()

        data class Failure(
            val message: String
        ) : SignupStatus()

        data class Success(
            val message: String
        ) : SignupStatus()

        sealed class Empty : SignupStatus() {
            object FirstName : Empty()
            object LastName : Empty()
            object Email : Empty()
            object Password : Empty()
            object Condition : Empty()
        }
    }
}