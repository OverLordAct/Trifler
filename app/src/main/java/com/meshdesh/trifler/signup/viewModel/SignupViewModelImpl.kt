package com.meshdesh.trifler.signup.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.localize.Localize
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.repository.SignupRepository
import kotlinx.coroutines.launch

class SignupViewModelImpl @ViewModelInject constructor(
    private val signupRepository: SignupRepository,
    private val localize: Localize
) : ViewModel(), SignupViewModel {

    override val signupStatusLiveData = MutableLiveData<SignupViewModel.SignupStatus>()

    override fun signup(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        conditionCheck: Boolean
    ) {
        signupStatusLiveData.value = SignupViewModel.SignupStatus.Loading

        var flag = false

        if (firstName.isBlank()) {
            signupStatusLiveData.value = SignupViewModel.SignupStatus.Empty.FirstName
            flag = true
        }

        if (lastName.isBlank()) {
            signupStatusLiveData.value = SignupViewModel.SignupStatus.Empty.LastName
            flag = true
        }

        if (email.isBlank()) {
            signupStatusLiveData.value = SignupViewModel.SignupStatus.Empty.Email
            flag = true
        }

        if (password.isBlank()) {
            signupStatusLiveData.value = SignupViewModel.SignupStatus.Empty.Password
            flag = true
        }

        if (!conditionCheck) {
            signupStatusLiveData.value = SignupViewModel.SignupStatus.Empty.Condition
            flag = true
        }

        if (flag) return

        viewModelScope.launch {
            val name = "$firstName $lastName"

            val signupRequest = SignupRequest(name, email, password)

            when (val response = signupRepository.signup(signupRequest)) {
                is Result.Success -> {
                    signupStatusLiveData.value =
                        SignupViewModel.SignupStatus.Success(response.data?.message.toString())
                }
                is Result.ServerError -> {
                    signupStatusLiveData.value =
                        SignupViewModel.SignupStatus.Failure(response.body?.message.toString())
                }
                is Result.NetworkError, is Result.UnknownError -> {
                    signupStatusLiveData.value = SignupViewModel.SignupStatus.Failure(
                        localize.localize(
                            R.string.signup_failure
                        )
                    )
                }
            }
        }
    }
}

