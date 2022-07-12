package com.geancarloleiva.a7_countriesmvvm.model

import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.util.BASE_URL
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryService {

    private val countryApi: CountryApi

    init {
        countryApi = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }

    fun getCountries(): Single<List<Country>>{
        return countryApi.getCountries()
    }
}