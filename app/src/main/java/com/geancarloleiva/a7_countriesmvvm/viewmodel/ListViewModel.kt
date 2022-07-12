package com.geancarloleiva.a7_countriesmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geancarloleiva.a7_countriesmvvm.Country

class ListViewModel: ViewModel() {

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
        //TODO: mocking data (instead of loading from backend)
        val mockData = listOf<Country>(
            Country("ABC0"),
            Country("ABC1"),
            Country("ABC2"),
            Country("ABC3"),
            Country("ABC4"),
            Country("ABC5"),
            Country("ABC6")
        )

        countryLoadError.value = false
        loading.value = false
        lstCountry.value = mockData
    }
}