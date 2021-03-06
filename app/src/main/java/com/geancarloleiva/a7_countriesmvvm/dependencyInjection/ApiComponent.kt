package com.geancarloleiva.a7_countriesmvvm.dependencyInjection

import com.geancarloleiva.a7_countriesmvvm.model.CountryService
import com.geancarloleiva.a7_countriesmvvm.viewmodel.ListViewModel
import dagger.Component

//We associate the Component to the Module that can be injected
@Component(modules = [ApiModule::class])
interface ApiComponent {

    //Defining the function that will inject the ApiModule to CountryService
    fun inject(service: CountryService)

    //Defining that I will inject in ListViewModel
    fun inject(viewModel: ListViewModel)
}