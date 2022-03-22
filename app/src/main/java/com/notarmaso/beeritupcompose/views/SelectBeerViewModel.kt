package com.notarmaso.beeritupcompose.views

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.models.GlobalBeer
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SelectBeerViewModel(serviceVm: Service) : ViewModel()  {
    var service = serviceVm



    fun setBeer(globalBeer: GlobalBeer){
        service.selectedGlobalBeer = globalBeer
    }

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }
}