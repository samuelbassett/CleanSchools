package com.tc.midtermproject.ui.authentication.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.midtermproject.data.Util.ResponseHandling
import com.tc.midtermproject.data.repository.firebase.AuthRepositoryImpl
import com.tc.midtermproject.ui.authentication.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
): ViewModel() {
    private val _signupState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val signupState: StateFlow<LoginState> = _signupState

    fun signupWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect { result ->
                when(result) {
                    is ResponseHandling.Loading -> {
                        _signupState.emit(LoginState(isLoading = true))
                    }
                    is ResponseHandling.Success -> {
                        _signupState.emit(LoginState(isSuccess = "Sign in Success"))
                    }
                    is ResponseHandling.Error -> {
                        _signupState.emit((LoginState(isError = result.message)))
                    }
                }
            }
        }
    }
}