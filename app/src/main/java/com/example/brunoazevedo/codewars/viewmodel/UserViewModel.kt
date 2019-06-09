package com.example.brunoazevedo.codewars.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel : ViewModel() {

    @Inject
    lateinit var repo : Repository

    private val displayUsers = ArrayList<User>()
    val users = MutableLiveData<List<User>>()
    val loadError = MutableLiveData<Boolean>()
    //is loading the data from backend?
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    init {
        DaggerAppComponent
            .create()
            .inject(this)
    }

    fun fetchUser(name : String) {
        loading.value = true
        disposable.add(
            repo.getUser(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<User>() {
                    override fun onError(e: Throwable) {
                        loading.value = false
                        loadError.value = true
                    }

                    override fun onSuccess(u: User) {
                        loading.value = false
                        loadError.value = true
                        //TODO : Find a way to only display the last 5 searches
                        displayUsers.add(u)
                        users.value = displayUsers
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}