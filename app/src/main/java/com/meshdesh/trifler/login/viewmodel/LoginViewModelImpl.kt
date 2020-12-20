package com.meshdesh.trifler.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.localize.Localize
import com.meshdesh.trifler.common.storage.account.AccountManager
import com.meshdesh.trifler.login.data.entity.LoginRequest
import com.meshdesh.trifler.login.data.entity.LoginResponse
import com.meshdesh.trifler.login.data.repository.LoginRepository
import com.meshdesh.trifler.login.viewmodel.LoginViewModel.LoginStatus
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelImpl @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val localize: Localize,
    private val accountManager: AccountManager
) : ViewModel(), LoginViewModel {

    override var loginStatusLiveData: MutableLiveData<LoginStatus> =
        MutableLiveData()

    private lateinit var login: Result<LoginResponse.Success, LoginResponse.Failure>

    fun startLogin(email: String?, password: String?) {
        loginStatusLiveData.value = LoginStatus.Loading
        when {
            !email.isNullOrEmpty() && !password.isNullOrEmpty() -> {
                val loginRequest = LoginRequest(email, password)

                viewModelScope.launch {
                    withContext(IO) {
                        login = loginRepository.login(loginRequest)
                    }

                    when (login) {
                        is Result.Success -> {
                            val data =
                                (login as Result.Success<LoginResponse.Success>).data
                            // TODO Set token once logged in
                            data?.user?.name?.let {
                                accountManager.login(
                                    it,
                                    data.user.email
                                )
                            }
                            loginStatusLiveData.value = LoginStatus.Success
                        }
                        is Result.ServerError -> {
                            val data =
                                (login as Result.ServerError<LoginResponse.Failure>).body
                            loginStatusLiveData.value =
                                data?.message?.let { LoginStatus.Failure(it) }
                        }
                        is Result.NetworkError, is Result.UnknownError -> {
                            loginStatusLiveData.value =
                                LoginStatus.Failure(localize.localize(R.string.login_failure))
                        }
                    }
                }
            }
            email.isNullOrEmpty() -> {
                loginStatusLiveData.value = LoginStatus.Blank.Email(
                    localize.localize(R.string.email_blank)
                )
            }
            password.isNullOrEmpty() -> {
                loginStatusLiveData.value = LoginStatus.Blank.Password(
                    localize.localize(R.string.password_blank)
                )
            }
            else -> {
                loginStatusLiveData.value = LoginStatus.Blank.Both(
                    localize.localize(R.string.email_blank),
                    localize.localize(R.string.password_blank)
                )
            }
        }
    }
}
