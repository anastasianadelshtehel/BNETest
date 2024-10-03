package com.example.bnetest.data

import com.example.bnetest.utils.Utils
import com.example.bnetest.utils.Utils.Companion.BASE_URL
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


interface BNetApi {


    @GET("api/ppp/index")
    suspend fun getCardMedication(
        @Query("id") id: Int
    ): Drugs


    @GET("api/ppp/index")
    suspend fun getListMedication(
        @Query("search") search: String?, @Query("offset") offset: Int, @Query("limit") limit: Int
    ): List<Drugs>
}


val retrofit = Retrofit.Builder().client(
    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()
).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    .create(BNetApi::class.java)