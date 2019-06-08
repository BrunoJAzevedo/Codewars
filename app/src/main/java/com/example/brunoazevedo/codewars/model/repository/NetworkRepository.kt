package com.example.brunoazevedo.codewars.model.repository

import com.example.brunoazevedo.codewars.model.User
import io.reactivex.Single

interface NetworkRepository {

    fun getUser(name : String) : Single<User>
}