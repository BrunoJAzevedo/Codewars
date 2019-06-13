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
    lateinit var repo: Repository

    private var firstTime = true

    val authoredChallenges = MutableLiveData<List<AuthoredChallengeData>>()
    val loading = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<Boolean>()
    var errorMessage : String? = ""

    private var retry = 0

    private val disposable = CompositeDisposable()

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getAuthoredChallenges(name : String?) {
        if (firstTime) {
            firstTime = !firstTime
            getAuthoredChallengesObservable(name)
        }
    }

    private fun getAuthoredChallengesObservable(name : String?) {
        loading.value = true
        loadError.value = false

        if (!name.isNullOrEmpty()) {
            disposable.add(
                repo.getAuthoredChallenges(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<AuthoredChallenges>() {
                        override fun onError(e: Throwable) {
                            firstTime = !firstTime
                            errorMessage = e.message
                            loading.value = false
                            loadError.value = true
                        }

                        override fun onSuccess(challenges : AuthoredChallenges) {
                            authoredChallenges.value = challenges.data
                            loading.value = false
                            loadError.value = false
                            retry = 0
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