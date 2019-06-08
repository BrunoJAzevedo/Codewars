package com.example.brunoazevedo.codewars.di

import com.example.brunoazevedo.codewars.model.api.CodewarsAPI
import com.example.brunoazevedo.codewars.model.api.CodewarsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://www.codewars.com/api/v1/"

    @Provides
    fun provideCodewarsAPI() : CodewarsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CodewarsAPI::class.java)
    }

    @Provides
    fun provideCodewarsService() = CodewarsService()

}