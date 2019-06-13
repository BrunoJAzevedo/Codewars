package com.example.brunoazevedo.codewars

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.brunoazevedo.codewars.model.*
import com.example.brunoazevedo.codewars.model.repository.Repository
import com.example.brunoazevedo.codewars.viewmodel.AuthoredChallengesViewModel
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

class AuthoredChallengesViewModelTesting {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: Repository

    @InjectMocks
    var viewModel = AuthoredChallengesViewModel()

    private lateinit var authoredChallengeOne : AuthoredChallengeData
    private lateinit var authoredChallengeTwo : AuthoredChallengeData
    private lateinit var challenges : AuthoredChallenges

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

        authoredChallengeOne = AuthoredChallengeData("one", "one", "description",
            10, "rank", listOf("Tag1", "Tag2"), listOf("languageOne", "languageTwo"))

        authoredChallengeTwo = AuthoredChallengeData("two", "two", "description",
            10, "rank", listOf("Tag2", "Tag3"), listOf("languageTwo", "languageThree"))

        challenges = AuthoredChallenges(listOf(authoredChallengeOne, authoredChallengeTwo))
    }

    @Test
    fun `get Authored Challenges`() {
        val testSingle = Single.just(challenges)

        Mockito.`when`(repo.getAuthoredChallenges("doe")).thenReturn(testSingle)

        viewModel.getAuthoredChallenges("doe")

        Assert.assertEquals(2, viewModel.authoredChallenges.value?.size)
        Assert.assertEquals(false, viewModel.loadError.value)
        Assert.assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `get Authored Challenges failure`() {
        val testSingle : Single<AuthoredChallenges>? = Single.error(Throwable())

        Mockito.`when`(repo.getAuthoredChallenges("doe")).thenReturn(testSingle)

        viewModel.getAuthoredChallenges("doe")

        Assert.assertEquals(true, viewModel.loadError.value)
        Assert.assertEquals(false, viewModel.loading.value)
    }
}