package com.geancarloleiva.a7_countriesmvvm.dependencyInjection

import com.geancarloleiva.a7_countriesmvvm.model.CountryApi
import com.geancarloleiva.a7_countriesmvvm.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//Indicates this class is a module that can be injected
@Module
class ApiModule {

    @Provides
    fun provideCountriesApi(): CountryApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }
}