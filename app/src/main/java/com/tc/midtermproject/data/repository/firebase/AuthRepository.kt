package com.tc.midtermproject.data.repository.firebase

import com.google.firebase.auth.AuthResult
import com.tc.midtermproject.data.Util.ResponseHandling
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<ResponseHandling<AuthResult>>

    fun registerUser(email: String, password: String): Flow<ResponseHandling<AuthResult>>
}