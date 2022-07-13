package com.geancarloleiva.a7_countriesmvvm.model

import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.dependencyInjection.DaggerApiComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CountryService {

    //Injecting the CountryApi (after this the project must be ReBuild)
    @Inject
    lateinit var countryApi: CountryApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>>{
        return countryApi.getCountries()
    }
}