package com.example.brunoazevedo.codewars

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.brunoazevedo.codewars.model.Overall
import com.example.brunoazevedo.codewars.model.Ranks
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.model.repository.Repository
import com.example.brunoazevedo.codewars.utils.shitftDown
import com.example.brunoazevedo.codewars.viewmodel.UserViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * This class tests the UserViewModel
 * and the UsersUtils functions
 */
class UserViewModelTesting {

    /**
    * A JUnit Test Rule that swaps the background executor used by the Architecture Components with a
    * different one which executes each task synchronously.
    * <p>
    * You can use this rule for your host side tests that use Architecture Components.
    */
    @get:Rule
    var rule = InstantTaskExecutorRule()


    @Mock
    lateinit var repo: Repository

    @InjectMocks
    var userViewModel = UserViewModel()

    private lateinit var john : User

    private lateinit var doe : User

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

        //Create users
        val languageOneJohn = Overall(10, "languageOneJohn", "color", 500)
        val languageTwoJohn = Overall(1, "languageTwoJohn", "color", 500)
        val languagesJohn = hashMapOf<String, Overall>()
        languagesJohn.put("languageOneJohn", languageOneJohn)
        languagesJohn.put("languageTwoJohn", languageTwoJohn)
        val overal = Overall(1, "name", "color", 1000)
        val ranks = Ranks(overal, languagesJohn)
        john = User("john", "john", 10, ranks)


        val languageOneDoe = Overall(10, "languageOneDoe", "color", 500)
        val languageTwoDoe = Overall(1, "languageTwoDoe", "color", 500)
        val languagesDoe = hashMapOf<String, Overall>()
        languagesDoe.put("languageOneDoe", languageOneDoe)
        languagesDoe.put("languageTwoDoe", languageTwoDoe)
        val overal2 = Overall(1, "name", "color", 1000)
        val ranks2 = Ranks(overal2, languagesDoe)
        doe = User("doe", "doe", 20, ranks2)
    }

    @Test
    fun `fetch User success`() {
        val testSingle = Single.just(john)

        `when`(repo.getUser("john")).thenReturn(testSingle)

        userViewModel.fetchUser("john")

        Assert.assertEquals(1, userViewModel.users.value?.size)
        Assert.assertEquals(false, userViewModel.loadError.value)
        Assert.assertEquals(false, userViewModel.loading.value)
    }

    @Test
    fun `fetch User failure`() {
        val testSingle : Single<User>? = Single.error(Throwable())

        `when`(repo.getUser("name")).thenReturn(testSingle)

        userViewModel.fetchUser("name")

        Assert.assertEquals(true, userViewModel.loadError.value)
        Assert.assertEquals(false, userViewModel.loading.value)
    }

    /**
     * Tests if orderByRank is ordering the users by rank
     */
    @Test
    fun `test is order by rank works correctly`() {
        val testSingle = Single.just(john)
        val testSingle2 = Single.just(doe)

        `when`(repo.getUser("john")).thenReturn(testSingle)
        `when`(repo.getUser("doe")).thenReturn(testSingle2)

        userViewModel.fetchUser("john")
        userViewModel.fetchUser("doe")

        Assert.assertEquals(2, userViewModel.users.value?.size)
        Assert.assertEquals("doe", userViewModel.users.value?.get(0)?.name)
        Assert.assertEquals("john", userViewModel.users.value?.get(1)?.name)

        userViewModel.orderByRank()

        Assert.assertEquals("john", userViewModel.users.value?.get(0)?.name)
        Assert.assertEquals("doe", userViewModel.users.value?.get(1)?.name)
    }

    @Test
    fun `test that there is no duplicated user`() {
        val testSingle = Single.just(john)
        val testSingle2 = Single.just(doe)

        `when`(repo.getUser("john")).thenReturn(testSingle)
        `when`(repo.getUser("doe")).thenReturn(testSingle2)

        userViewModel.fetchUser("john")
        userViewModel.fetchUser("doe")

        Assert.assertEquals("doe", userViewModel.users.value?.get(0)?.name)
        Assert.assertEquals("john", userViewModel.users.value?.get(1)?.name)

        userViewModel.fetchUser("john")

        Assert.assertEquals(2, userViewModel.users.value?.size)
        Assert.assertEquals("john", userViewModel.users.value?.get(0)?.name)
        Assert.assertEquals("doe", userViewModel.users.value?.get(1)?.name)
    }

    @Test
    fun `test if ordering by search works`() {
        val testSingle = Single.just(john)
        val testSingle2 = Single.just(doe)

        `when`(repo.getUser("john")).thenReturn(testSingle)
        `when`(repo.getUser("doe")).thenReturn(testSingle2)

        userViewModel.fetchUser("john")
        userViewModel.fetchUser("doe")

        Assert.assertEquals("doe", userViewModel.users.value?.get(0)?.name)
        Assert.assertEquals("john", userViewModel.users.value?.get(1)?.name)
    }

    @Test
    fun `test method that adds User to the top of the list`() {
        val languageOne = Overall(10, "languageOne", "color", 500)
        val languageTwo = Overall(1, "languageTwo", "color", 500)
        val languages = hashMapOf<String, Overall>()
        languages.put("languageOneJohn", languageOne)
        languages.put("languageTwoJohn", languageTwo)
        val overal = Overall(1, "name", "color", 1000)
        val ranks = Ranks(overal, languages)
        var aux = User("aux", "aux", 10, ranks)

        val list = listOf(doe, aux)

        var res = shitftDown(list, john)

        Assert.assertEquals("john", res.get(0).name)
        Assert.assertEquals("doe", res.get(1).name)
        Assert.assertEquals("aux", res.get(2).name)
    }

}