package com.example.brunoazevedo.codewars.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.AuthoredChallengeData
import com.example.brunoazevedo.codewars.model.AuthoredChallenges
import com.example.brunoazevedo.codewars.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthoredChallengesViewModel : ViewModel() {

    @Inject
    lateinit var _repo: Repository

    private var _firstTime = true

    val _authoredChallenges = MutableLiveData<List<AuthoredChallengeData>>()
    val _loading = MutableLiveData<Boolean>()
    val _loadError = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getAuthoredChallenges(name : String?) {
        if (_firstTime) {
            _firstTime = !_firstTime
            getAuthoredChallengesObservable(name)
        }
    }

    private fun getAuthoredChallengesObservable(name : String?) {
        _loading.value = true
        _loadError.value = false

        if (!name.isNullOrEmpty()) {
            disposable.add(
                _repo.getAuthoredChallenges(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<AuthoredChallenges>() {
                        override fun onError(e: Throwable) {
                            _loading.value = false
                            _loadError.value = true
                        }

                        override fun onSuccess(challenges : AuthoredChallenges) {
                            _authoredChallenges.value = challenges.data
                            _loading.value = false
                            _loadError.value = false
                        }
                    })
            )
        }

    }
}