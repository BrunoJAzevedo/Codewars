package com.example.brunoazevedo.codewars.model.api

import com.example.brunoazevedo.codewars.model.AuthoredChallenges
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import com.example.brunoazevedo.codewars.model.Challenge
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.utils.AUTHORED_CHALLENGES
import com.example.brunoazevedo.codewars.utils.CODE_CHALLENGES
import com.example.brunoazevedo.codewars.utils.COMPLETED_CHALLENGES
import com.example.brunoazevedo.codewars.utils.USER
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CodewarsAPI {

    @GET(USER)
    fun getUser(
        @Path("name") name: String
    ): Single<User>

    @GET(COMPLETED_CHALLENGES)
    fun getCompletedChallenges(
        @Path("name") name: String,
        @Query("page") page: Int
    ): Observable<CompletedChallenges>

    @GET(AUTHORED_CHALLENGES)
    fun getAuthoredChallenges(
        @Path("name") name : String
    ) : Single<AuthoredChallenges>

    @GET(CODE_CHALLENGES)
    fun getChallengeInfo(
        @Path("id") id : String
    ) : Single<Challenge>


}