package com.example.stora.network

import com.example.stora.data.AuthResponse
import com.example.stora.data.LoginRequest
import com.example.stora.data.SignupRequest
import com.example.stora.data.UpdateProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>
    
    @POST("signup")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ): Response<AuthResponse>
    
    @POST("logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<AuthResponse>
    
    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<AuthResponse>
    
    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<AuthResponse>
}
