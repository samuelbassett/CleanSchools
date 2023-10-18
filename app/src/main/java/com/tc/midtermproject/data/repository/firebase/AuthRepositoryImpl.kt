package com.tc.midtermproject.data.repository.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tc.midtermproject.data.Util.ResponseHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override fun loginUser(email: String, password: String): Flow<ResponseHandling<AuthResult>> {
        return flow {
            emit(ResponseHandling.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(ResponseHandling.Success(result))
        }.catch {
            emit(ResponseHandling.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<ResponseHandling<AuthResult>> {
        return flow {
            emit(ResponseHandling.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(ResponseHandling.Success(result))
        }.catch {
            emit(ResponseHandling.Error(it.message.toString()))
        }
    }
}