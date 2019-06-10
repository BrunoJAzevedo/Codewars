package com.example.brunoazevedo.codewars.model

data class User(val name : String, val username : String,  val leaderboardPosition : Long, val ranks : Ranks )

data class Ranks(val overall : Overall, val languages : Map<String, Overall>)

data class Overall(val rank : Long, val name : String, val color : String, val score : Long)
