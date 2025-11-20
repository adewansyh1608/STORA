package com.example.stora.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    // Base URL - ganti dengan URL API Anda
    // Untuk development local: "http://10.0.2.2:3000/api/" (Android Emulator)
    // Untuk device fisik: "http://YOUR_LOCAL_IP:3000/api/"
    // Jika error 404, coba ganti ke "http://10.0.2.2:3000/" tanpa /api/
    private const val BASE_URL = "http://10.0.2.2:3000/api/v1/"
    
    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    fun provideApiService(): ApiService {
        return provideRetrofit().create(ApiService::class.java)
    }
}
