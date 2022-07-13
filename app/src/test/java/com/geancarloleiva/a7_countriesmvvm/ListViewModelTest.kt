package com.geancarloleiva.a7_countriesmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geancarloleiva.a7_countriesmvvm.model.CountryService
import com.geancarloleiva.a7_countriesmvvm.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
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

class ListViewModelTest {

    //Configuring the test
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countryService: CountryService
    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    //This is the test that the system must run
    @Test
    fun getCountriesSuccess(){
        //Setting up the test
        val country = Country("name", "capital", "url")
        val lstCountry = arrayListOf(country)

        testSingle = Single.just(lstCountry)

        Mockito.`when`(countryService.getCountries()).thenReturn(testSingle)

        //Calling the real functionality
        listViewModel.refresh()

        //Executing the tests
        Assert.assertEquals(1, listViewModel.lstCountry.value?.size)
        Assert.assertEquals(false, listViewModel.countryLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    //This is the test that the system must run
    @Test
    fun getCountriesFail(){
        testSingle = Single.error(Throwable())
        Mockito.`when`(countryService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()

        Assert.assertEquals(true, listViewModel.countryLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    //Previous configuration for RxJava and RxAndroid to response immediately
    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            //When an observable is called, we need to return immediately
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {
                    it.run()
                }, false, true)
            }
        }

        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { sheduler -> immediate }
    }
}