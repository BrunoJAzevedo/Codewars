package com.example.brunoazevedo.codewars.model.repository

import com.example.brunoazevedo.codewars.model.AuthoredChallenges
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import com.example.brunoazevedo.codewars.model.User
import io.reactivex.Observable
import io.reactivex.Single

interface NetworkRepository {

    fun getUser(name : String) : Single<User>

    fun getCompletedChallenges(name : String, page : Int) : Observable<CompletedChallenges>

    fun getAuthoredChallenges(name : String) : Single<AuthoredChallenges>
}