package com.example.brunoazevedo.codewars.model.api

import com.example.brunoazevedo.codewars.model.User
import io.reactivex.Single
import javax.inject.Inject

class CodewarsService {

    @Inject
    lateinit var api : UserAPI

    fun getUser(name : String) : Single<User> {
        return api.getUser(name)
    }
}