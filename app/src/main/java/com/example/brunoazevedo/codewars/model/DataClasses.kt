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

/*
Info Challenge
 */
data class InfoChallenge(val id : String, val name : String, val category : String, val publishedAt : String,
                         val approvedAt : String, val languages : List<String>, val url : String,
                         val rank : Rank, val createdAt : String, val createdBy : UserInfo,
                         val approvedBy : UserInfo, val description : String, val totalAttempts : Int,
                         val totalCompleted : Int, val totalStars : Int, val voteScore : Int,
                         val tags : List<String>,
                         @SerializedName("contributorsWanted")val helpNeeded : Boolean,
                         val unresolved : Unresolved)

data class Rank(val id : Int, val name : String, val color : String)

data class UserInfo(val username : String, val url : String)

data class Unresolved(val issues : Int, val suggestions : Int)