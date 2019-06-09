package com.example.brunoazevedo.codewars.di

import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.model.api.CodewarsService
import com.example.brunoazevedo.codewars.model.repository.Repository
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service : CodewarsService)

    fun inject(repository : Repository)

    fun inject(viewModel: ViewModel)
}