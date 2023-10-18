package com.tc.midtermproject.ui.authentication.login

data class LoginState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)