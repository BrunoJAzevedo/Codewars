package com.example.brunoazevedo.codewars.model.api

import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.utils.USER
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface CodewarsAPI {

    @GET(USER)
    fun getUser(@Path("name") name: String): Single<User>

}