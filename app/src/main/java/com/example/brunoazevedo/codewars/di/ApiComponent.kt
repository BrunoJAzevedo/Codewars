package com.example.brunoazevedo.codewars.di

import com.example.brunoazevedo.codewars.model.api.CodewarsService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service : CodewarsService)
}