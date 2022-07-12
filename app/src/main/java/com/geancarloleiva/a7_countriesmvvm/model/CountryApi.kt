package com.geancarloleiva.a7_countriesmvvm.model

import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.util.GET_COUNTRIES_API
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryApi {

    @GET(GET_COUNTRIES_API)
    fun getCountries(): Single<List<Country>>
}