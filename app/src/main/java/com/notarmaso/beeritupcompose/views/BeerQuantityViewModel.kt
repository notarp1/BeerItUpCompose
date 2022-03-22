package com.notarmaso.beeritupcompose.views

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Service
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class BeerQuantityViewModel(serviceVm: Service): ViewModel() {

    var service = serviceVm

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }
}