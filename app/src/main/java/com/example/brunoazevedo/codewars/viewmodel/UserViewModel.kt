package com.example.brunoazevedo.codewars.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.model.repository.Repository
import com.example.brunoazevedo.codewars.utils.orderByRankUtil
import com.example.brunoazevedo.codewars.utils.shitftDown
import com.example.brunoazevedo.codewars.utils.userToTop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel : ViewModel() {

    @Inject
    lateinit var repo : Repository

    private var displayUsers = ArrayList<User>()
    private var rankOrder = false
    val users = MutableLiveData<List<User>>()
    val loadError = MutableLiveData<Boolean>()
    //is loading the data from backend?
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    var errorMessage : String? = ""

    init {
        DaggerAppComponent
            .create()
            .inject(this)
    }

    /**
     * Start error and loading as false
     */
    fun init() {
        loading.value = false
        loadError.value = false
    }

    fun fetchUser(name : String) {
        loading.value = true
        disposable.add(
            repo.getUser(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<User>() {
                    override fun onError(e: Throwable) {
                        errorMessage = e.message
                        loading.value = false
                        loadError.value = true
                    }

                    override fun onSuccess(u: User) {
                        if (!displayUsers.contains(u)) {
                            if (displayUsers.isNotEmpty()) displayUsers = shitftDown(displayUsers, u)
                            else displayUsers.add(u)
                            users.value = if(!rankOrder) displayUsers else orderByRankUtil(displayUsers)

                        } else {
                            if(!rankOrder) {
                                displayUsers = userToTop(displayUsers, u)
                                users.value = displayUsers
                            }
                        }
                        loading.value = false
                        loadError.value = false
                    }
                })
        )
    }

    fun orderByRank() {
        rankOrder = true
        users.value =  orderByRankUtil(displayUsers)
    }

    fun orderBySearchTime() {
        rankOrder = false
        users.value = displayUsers
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}