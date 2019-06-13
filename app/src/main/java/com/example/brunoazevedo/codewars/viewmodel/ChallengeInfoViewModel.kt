package com.example.brunoazevedo.codewars.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.Challenge
import com.example.brunoazevedo.codewars.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChallengeInfoViewModel : ViewModel() {

    @Inject
    lateinit var repo : Repository

    val challenge = MutableLiveData<Challenge>()
    val loading = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<Boolean>()
    var errorMessage : String? = ""

    private val disposable = CompositeDisposable()

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getChallengeInfo(id : String?) {
        loading.value = true
        loadError.value = false

        if (!id.isNullOrEmpty()) {
            disposable.add(
                repo.getChallengeInfo(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Challenge>() {
                        override fun onError(e: Throwable) {
                            errorMessage = e.message
                            loading.value = false
                            loadError.value = true
                        }

                        override fun onSuccess(challenges : Challenge) {
                            challenge.value = challenges
                            loading.value = false
                            loadError.value = false
                        }
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}