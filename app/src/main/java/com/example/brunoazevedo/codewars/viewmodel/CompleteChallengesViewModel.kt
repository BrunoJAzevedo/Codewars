package com.example.brunoazevedo.codewars.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.brunoazevedo.codewars.di.DaggerAppComponent
import com.example.brunoazevedo.codewars.model.CompletedChallengeData
import com.example.brunoazevedo.codewars.model.CompletedChallenges
import com.example.brunoazevedo.codewars.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CompleteChallengesViewModel : ViewModel() {

    @Inject
    lateinit var repo : Repository

    private var firstTime = true

    private var challengesCompletedArrayList = ArrayList<CompletedChallengeData>()

    val challengesCompleted = MutableLiveData<List<CompletedChallengeData>>()

    val loading = MutableLiveData<Boolean>()
    val allCompletedChallengesObtained = MutableLiveData<Boolean>()
    val errorLoad = MutableLiveData<Boolean>()
    var errorMessage : String? = ""

    private lateinit var disposable : Disposable

    private var pageCounter = 0
    /*
    * All the users have at least one page with completed challenges
    * with the sign in challenge
    */
    private var pageNumber = 1
    private var retry = 0


    init {
        DaggerAppComponent.create().inject(this)
    }

    /**
     * With this method we can alternate between authored and completed challenges fragments
     * without being constantly fetching the completed challenges
     * without the scroll
     */
    fun getCompletedChallengesPageInit(name : String?) {
        if (firstTime) {
            firstTime = !firstTime
            getCompletedChallengesPage(name)
        }
    }

    fun getCompletedChallengesPage(name : String?) {
        loading.value = true
        allCompletedChallengesObtained.value = false
        errorLoad.value = false

        if ((pageCounter < pageNumber) && !name.isNullOrEmpty()) {
            disposable = repo.getCompletedChallenges(name, pageCounter++)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CompletedChallenges>() {

                    override fun onComplete() {
                        disposable.dispose()
                    }

                    override fun onNext(t : CompletedChallenges) {
                        loading.value = false
                        pageNumber = t.totalPages
                        challengesCompletedArrayList.addAll(t.data)
                        challengesCompleted.value = challengesCompletedArrayList

                        //reset retry
                        retry = 0

                        disposable.dispose()
                    }

                    override fun onError(e: Throwable) {
                        pageCounter--
                        firstTime = !firstTime
                        errorMessage = e.message
                        loading.value = false
                        errorLoad.value = true
                    }
                })
        } else {
            loading.value = false
            allCompletedChallengesObtained.value = true
            errorLoad.value = false
        }

    }

}