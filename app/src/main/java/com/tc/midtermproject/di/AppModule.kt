package com.tc.midtermproject.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.tc.midtermproject.data.remote.ApiDetails
import com.tc.midtermproject.data.remote.ApiEndpoint
import com.tc.midtermproject.data.repository.firebase.AuthRepositoryImpl
import com.tc.midtermproject.data.repository.service.Repository
import com.tc.midtermproject.data.repository.service.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Module > responsible for defining definition of injection
@InstallIn(SingletonComponent::class) // dictates the scope of injection
class AppModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor {
                val request =
                    it.request()
                        .newBuilder()
                        .build()
                it.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiClient(
        retrofit: Retrofit
    ): ApiEndpoint {
        return retrofit.create(ApiEndpoint::class.java)
    }

    @Provides
    fun provideRepository(
        apiClient: ApiEndpoint
    ): Repository {
        return RepositoryImpl(apiClient)
    }

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepositoryImpl {
        return AuthRepositoryImpl(firebaseAuth)
    }
}