package com.example.brunoazevedo.codewars.model

import com.google.gson.annotations.SerializedName


/*
User
 */
data class User(val name : String, val username : String,  val leaderboardPosition : Long,
                val ranks : Ranks )

data class Ranks(val overall : Overall, val languages : Map<String, Overall>)

data class Overall(val rank : Long, val name : String, val color : String, val score : Long)

/*
Completed Challenge
 */
data class CompletedChallenges(val totalPages : Int, val totalItems : Long,
                              val data : List<CompletedChallengeData>)

data class CompletedChallengeData(val id : String, val name : String, val slug : String,
                                  @SerializedName("completedLanguages")val languages : List<String>,
                                  val completedAt : String)


/*
Authored Challenge
 */

data class AuthoredChallenges(val data : List<AuthoredChallengeData>)

data class AuthoredChallengeData(val id : String, val name : String, val description : String,
                                 val ranks : Long, val rankName : String, val tags : List<String>,
                                 val languages : List<String>)

