package com.geancarloleiva.a7_countriesmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.dependencyInjection.DaggerApiComponent
import com.geancarloleiva.a7_countriesmvvm.model.CountryService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel: ViewModel() {

    //Injecting the CountryService
    @Inject
    lateinit var countryService: CountryService
    init {
        DaggerApiComponent.create().inject(this)
    }
    //Used to close connection to the backend
    private val disposable = CompositeDisposable()

    /* Implementing reactivity (observer pattern) */
    //Live data: a component can subscribe to it, and it'll be notified automatically when the data change
    val lstCountry = MutableLiveData<List<Country>>()
    //Notify when an error loading the data occurs
    val countryLoadError = MutableLiveData<Boolean>()
    //Indicates if the data is been loading by the backend
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries(){
        loading.value = true
        disposable.add(
            countryService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(value: List<Country>) {
                        lstCountry.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}