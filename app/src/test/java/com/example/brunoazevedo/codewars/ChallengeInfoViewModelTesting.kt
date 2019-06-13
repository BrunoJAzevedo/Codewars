package com.example.brunoazevedo.codewars

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.brunoazevedo.codewars.model.Challenge
import com.example.brunoazevedo.codewars.model.Rank
import com.example.brunoazevedo.codewars.model.Unresolved
import com.example.brunoazevedo.codewars.model.UserInfo
import com.example.brunoazevedo.codewars.model.repository.Repository
import com.example.brunoazevedo.codewars.viewmodel.ChallengeInfoViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ChallengeInfoViewModelTesting {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: Repository

    @InjectMocks
    var viewModel = ChallengeInfoViewModel()

    private lateinit var unresolved: Unresolved
    private lateinit var userInfo: UserInfo
    private lateinit var rank: Rank
    private lateinit var challenge : Challenge

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        unresolved = Unresolved(5,10)

        userInfo = UserInfo("userInfo", "url")

        rank = Rank(1, "name", "color")

        challenge = Challenge("id","name", "category", "pubAt",
            "approved", listOf("languagesOne", "languageTwo"), "url", rank,
            "createdAt", userInfo, userInfo, "description", 10,
            5, 8, 6, listOf("tagOne", "tagTwo"), false,
            unresolved)
    }

    @Test
    fun `get Challenge Info`() {
        val testSingle = Single.just(challenge)

        Mockito.`when`(repo.getChallengeInfo("id")).thenReturn(testSingle)

        viewModel.getChallengeInfo("id")

        Assert.assertEquals("name", viewModel._challenge.value?.name)
        Assert.assertEquals(false, viewModel._loadError.value)
        Assert.assertEquals(false, viewModel._loading.value)
    }

    @Test
    fun `get Authored Challenges failure`() {
        val testSingle : Single<Challenge>? = Single.error(Throwable())

        Mockito.`when`(repo.getChallengeInfo("id")).thenReturn(testSingle)

        viewModel.getChallengeInfo("id")

        Assert.assertEquals(true, viewModel._loadError.value)
        Assert.assertEquals(false, viewModel._loading.value)
    }
}