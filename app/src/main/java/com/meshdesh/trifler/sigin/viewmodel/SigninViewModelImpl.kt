package com.meshdesh.trifler.sigin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.localize.Localize
import com.meshdesh.trifler.common.storage.account.AccountManager
import com.meshdesh.trifler.sigin.data.entity.SigninRequest
import com.meshdesh.trifler.sigin.data.repository.SigninRepository
import com.meshdesh.trifler.sigin.viewmodel.SigninViewModel.SigninStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModelImpl @Inject constructor(
    private val loginRepository: SigninRepository,
    private val localize: Localize,
    private val accountManager: AccountManager
) : ViewModel(), SigninViewModel {

    override var signinStatusLiveData: MutableLiveData<SigninStatus> =
        MutableLiveData()

    override fun startSignin(email: String, password: String) {
        signinStatusLiveData.value = SigninStatus.Loading

        var flag = false

        if (email.isBlank()) {
            signinStatusLiveData.value = SigninStatus.Blank.Email
            flag = true
        }

        if (password.isBlank()) {
            signinStatusLiveData.value = SigninStatus.Blank.Password
            flag = true
        }

        if (flag) return

        val loginRequest = SigninRequest(email, password)

        viewModelScope.launch {

            when (val login = loginRepository.login(loginRequest)) {
                is Result.Success -> {
                    val userData = login.data?.user
                    userData?.let {
                        accountManager.login(
                            it.userId,
                            it.phone,
                            it.accessToken,
                            it.refreshToken
                        )
                    }
                    signinStatusLiveData.value =
                        SigninStatus.Success(login.data?.message.toString())
                }
                is Result.ServerError -> {
                    signinStatusLiveData.value =
                        SigninStatus.Failure(login.body?.message.toString())
                }
                is Result.NetworkError, is Result.UnknownError -> {
                    signinStatusLiveData.value =
                        SigninStatus.Failure(localize.localize(R.string.login_failure))
                }
            }
        }
    }
}
