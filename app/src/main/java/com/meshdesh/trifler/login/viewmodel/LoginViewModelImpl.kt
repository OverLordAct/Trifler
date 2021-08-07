package com.meshdesh.trifler.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.localize.Localize
import com.meshdesh.trifler.common.storage.account.AccountManager
import com.meshdesh.trifler.login.data.entity.LoginRequest
import com.meshdesh.trifler.login.data.repository.LoginRepository
import com.meshdesh.trifler.login.viewmodel.LoginViewModel.LoginStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val localize: Localize,
    private val accountManager: AccountManager
) : ViewModel(), LoginViewModel {

    override var loginStatusLiveData: MutableLiveData<LoginStatus> =
        MutableLiveData()

    override fun startLogin(email: String, password: String) {
        loginStatusLiveData.value = LoginStatus.Loading

        var flag = false

        if (email.isBlank()) {
            loginStatusLiveData.value = LoginStatus.Blank.Email
            flag = true
        }

        if (password.isBlank()) {
            loginStatusLiveData.value = LoginStatus.Blank.Password
            flag = true
        }

        if (flag) return

        val loginRequest = LoginRequest(email, password)

        viewModelScope.launch {
            when (val login = loginRepository.login(loginRequest)) {
                is Result.Success -> {
                    val userData = login.data?.payload
                    userData?.let {
                        accountManager.login(
                            it.userId,
                            it.phone,
                            it.accessToken,
                            it.refreshToken
                        )
                    }
                    loginStatusLiveData.value =
                        LoginStatus.Success
                }
                is Result.ServerError -> {
                    loginStatusLiveData.value =
                        LoginStatus.Failure(
                            login.body?.message ?: localize.localize(R.string.login_failure)
                        )
                }
                is Result.NetworkError, is Result.UnknownError -> {
                    loginStatusLiveData.value =
                        LoginStatus.Failure(localize.localize(R.string.login_failure))
                }
            }
        }
    }
}
