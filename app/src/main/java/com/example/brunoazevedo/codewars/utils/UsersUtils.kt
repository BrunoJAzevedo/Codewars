package com.example.brunoazevedo.codewars.utils

import com.example.brunoazevedo.codewars.model.Overall
import com.example.brunoazevedo.codewars.model.User

/**
 * Will return an List of users ordered by rank
 */
fun orderByRankUtil(users : List<User>) : List<User> {
    return dealWith0Rank(users.sortedBy { it.leaderboardPosition })
}

/**
 * When a persons doesn't have a rank will have the leaderboardPosition has 0.
 * In this case will be the last person in the list
 */
private fun dealWith0Rank(users : List<User>) : List<User> {
    var aux = users
    while (aux[0].leaderboardPosition == 0L && aux.size > 1 && !onlyValidRank(users)) {
        aux = shiftUp(aux, aux[0])
    }
    return deleteExtraUser(aux)
}

/**
 * Insert user in the last element of the List
 * Moves the users in the original list up
 */
private fun shiftUp(users : List<User>, user : User) : List<User> {
    var aux = ArrayList<User>(users)
    for (i in 1 until users.size) {
        aux[i-1] = aux[i]
    }
    aux[users.size -1] = user
    return aux
}

/**
 * True: all the Users have a valid rank (leaderboardRank >=1)
 */
private fun onlyValidRank(users : List<User>) : Boolean {
    var isValid = false
    for (i in 0 until users.size) {
        if (users[i].leaderboardPosition > 0) isValid = true; break
    }
    return isValid
}

/**
 * Inserts new User on the top of the List
 */
fun shitftDown(users : List<User>, user : User) : ArrayList<User> {
    var aux = ArrayList<User>()
    aux.add(user)
    for (i in 0 until users.size) {
        aux.add(users[i])
    }
    return deleteExtraUser(aux)
}

/**
 * Used when the searched user
 * is already on the list
 * Move it to the top
 */
fun userToTop(users: List<User>, user: User) : ArrayList<User> {
    var aux = ArrayList<User>(users)
    aux.remove(user)
    return shitftDown(aux, user)
}

/**
 * Use this method to only display 5 elements
 */
private fun deleteExtraUser(users : List<User>) : ArrayList<User> {
    var aux = ArrayList<User>(users)
    if (aux.size > 5) {
        aux.removeAt(5)
    }
    return aux
}

/**
 * Returns user best language
 */
fun userBestLanguage(languages : Map<String, Overall>) : String {
    var auxLanguage = ""
    var auxBestPoints = -1L
    languages.forEach { (language, overal) ->
        if (overal.score > auxBestPoints) {
            auxLanguage = language
            auxBestPoints = overal.score
        }
    }
    return auxLanguage
}

/**
 * Return the score for a given language
 */
fun languagePoints(language : Overall?) : Long? = language?.score

