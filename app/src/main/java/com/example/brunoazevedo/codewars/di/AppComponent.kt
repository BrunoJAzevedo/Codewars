package com.example.brunoazevedo.codewars.di

import com.example.brunoazevedo.codewars.model.api.CodewarsService
import com.example.brunoazevedo.codewars.model.repository.Repository
import com.example.brunoazevedo.codewars.viewmodel.AuthoredChallengesViewModel
import com.example.brunoazevedo.codewars.viewmodel.CompleteChallengesViewModel
import com.example.brunoazevedo.codewars.viewmodel.ChallengeInfoViewModel
import com.example.brunoazevedo.codewars.viewmodel.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ApiModule::class
])
@Singleton
interface AppComponent {

    fun inject(repo : Repository)
    fun inject(service : CodewarsService)
    fun inject(userViewMode : UserViewModel)
    fun inject(completeChallengesViewModel: CompleteChallengesViewModel)
    fun inject(authoredChallengesViewModel : AuthoredChallengesViewModel)
    fun inject(authoredChallengesInfoViewModel : ChallengeInfoViewModel)

}