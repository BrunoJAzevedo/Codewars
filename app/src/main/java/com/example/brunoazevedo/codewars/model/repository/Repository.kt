package com.example.brunoazevedo.codewars.model.repository

import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.AuthoredChallenges
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.model.api.CodewarsService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class Repository : NetworkRepository {

    @Inject
    lateinit var _service : CodewarsService

    init {
        DaggerAppComponent.create().inject(this)
    }

    override fun getUser(name: String): Single<User> {
        return _service.getUser(name)
    }

    override fun getCompletedChallenges(name: String, page: Int): Observable<CompletedChallenges> {
        return _service.getCompletedChallenges(name, page)
    }


    override fun getAuthoredChallenges(name: String): Single<AuthoredChallenges> {
        return _service.getAuthoredChallenges(name)
    }
}