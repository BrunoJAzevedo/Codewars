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
    lateinit var _repo : Repository

    private var _firstTime = true

    private var _challengesCompletedArrayList = ArrayList<CompletedChallengeData>()

    val _challengesCompleted = MutableLiveData<List<CompletedChallengeData>>()

    val _loading = MutableLiveData<Boolean>()
    val _allCompletedChallengesObtained = MutableLiveData<Boolean>()
    val _error = MutableLiveData<Boolean>()

    private lateinit var _disposable : Disposable

    private var _pageCounter = 0
    /*
    * All the users have at least one page with completed challenges
    * with the sign in challenge
    */
    private var _pageNumber = 1


    init {
        DaggerAppComponent.create().inject(this)
    }

    /**
     * With this method we can alternate between authored and completed challenges fragments
     * without being constantly fetching the completed challenges
     * without the scroll
     */
    fun getCompletedChallengesPageInit(name : String?) {
        if (_firstTime) {
            _firstTime = !_firstTime
            getCompletedChallengesPage(name)
        }
    }

    fun getCompletedChallengesPage(name : String?) {
        _loading.value = true
        _allCompletedChallengesObtained.value = false
        _error.value = false

        if ((_pageCounter < _pageNumber) && !name.isNullOrEmpty()) {
            _disposable = _repo.getCompletedChallenges(name, _pageCounter++)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CompletedChallenges>() {

                    override fun onComplete() {
                        _disposable.dispose()
                    }

                    override fun onNext(t : CompletedChallenges) {
                        _loading.value = false
                        _pageNumber = t.totalPages
                        _challengesCompletedArrayList.addAll(t.data)
                        _challengesCompleted.value = _challengesCompletedArrayList
                        _disposable.dispose()
                    }

                    override fun onError(e: Throwable) {
                        _error.value = true
                        _disposable.dispose()
                    }
                })
        } else {
            _loading.value = false
            _allCompletedChallengesObtained.value = true
            _error.value = false
        }

    }

}