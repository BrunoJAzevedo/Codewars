package com.example.brunoazevedo.codewars.di

import android.support.v4.app.Fragment
import com.example.brunoazevedo.codewars.model.api.CodewarsService
import com.example.brunoazevedo.codewars.model.repository.Repository
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
    fun inject(fragment : Fragment)
    fun inject(viewModel : UserViewModel)

}