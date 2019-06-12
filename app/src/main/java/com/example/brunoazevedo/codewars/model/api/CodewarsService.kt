package com.example.brunoazevedo.codewars.model.api

import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import io.reactivex.Observable
import javax.inject.Inject

class CodewarsService {

    @Inject
    lateinit var _api : CodewarsAPI

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getUser(name : String) =_api.getUser(name)


    fun getCompletedChallenges(name : String, page : Int) : Observable<CompletedChallenges> {
        return _api.getCompletedChallenges(name,page)
    }

    fun getAuthoredChallenges(name : String) = _api.getAuthoredChallenges(name)

    fun getChallengeInfo(id : String) = _api.getChallengeInfo(id)
}