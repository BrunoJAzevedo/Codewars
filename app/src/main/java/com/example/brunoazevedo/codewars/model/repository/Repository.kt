package com.example.brunoazevedo.codewars.model.repository

import com.example.brunoazevedo.codewars.di.DaggerApiComponent
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.model.api.CodewarsService
import io.reactivex.Single
import javax.inject.Inject

class Repository : NetworkRepository {

    @Inject
    lateinit var service : CodewarsService

    init {
        DaggerApiComponent.create().inject(this)
    }

    override fun getUser(name: String): Single<User> {
        return this.service.getUser(name)
    }
}