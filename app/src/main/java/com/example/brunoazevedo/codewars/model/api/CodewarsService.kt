package com.example.brunoazevedo.codewars.model.api

import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import io.reactivex.Observable
import javax.inject.Inject

class CodewarsService {

    @Inject
    lateinit var api : CodewarsAPI

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getUser(name : String) =api.getUser(name)


    fun getCompletedChallenges(name : String, page : Int) : Observable<CompletedChallenges> {
        return api.getCompletedChallenges(name,page)
    }

    fun getAuthoredChallenges(name : String) = api.getAuthoredChallenges(name)

    fun getChallengeInfo(id : String) = api.getChallengeInfo(id)
}