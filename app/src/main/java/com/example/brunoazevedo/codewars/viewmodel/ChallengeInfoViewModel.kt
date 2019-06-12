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
    lateinit var _repo : Repository

    val _challenge = MutableLiveData<Challenge>()
    val _loading = MutableLiveData<Boolean>()
    val _loadError = MutableLiveData<Boolean>()
    var _errorMessage : String? = ""

    private val disposable = CompositeDisposable()

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getChallengeInfo(id : String?) {
        _loading.value = true
        _loadError.value = false

        if (!id.isNullOrEmpty()) {
            disposable.add(
                _repo.getChallengeInfo(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Challenge>() {
                        override fun onError(e: Throwable) {
                            _errorMessage = e.message
                            _loading.value = false
                            _loadError.value = true
                        }

                        override fun onSuccess(challenges : Challenge) {
                            _challenge.value = challenges
                            _loading.value = false
                            _loadError.value = false
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