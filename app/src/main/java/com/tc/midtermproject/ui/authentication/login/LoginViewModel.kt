package com.tc.midtermproject.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.midtermproject.data.Util.ResponseHandling
import com.tc.midtermproject.data.repository.firebase.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
): ViewModel() {
    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun loginWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect { result ->
                when(result) {
                    is ResponseHandling.Loading -> {
                        _loginState.emit(LoginState(isLoading = true))
                    }
                    is ResponseHandling.Success -> {
                        _loginState.emit(LoginState(isSuccess = "Sign in Success"))
                    }
                    is ResponseHandling.Error -> {
                        _loginState.emit((LoginState(isError = result.message)))
                    }
                }
            }
        }
    }
}