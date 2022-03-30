package com.notarmaso.beeritupcompose.views.debugDrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DebugDrawerViewModel(val service: Service, private val beerService: BeerService): ViewModel() {
    fun removeUsers() {
        viewModelScope.launch(Dispatchers.IO){
            service.db.userDao().deleteAll()
        }

    }

    fun removeBeers() {
        viewModelScope.launch(Dispatchers.IO){
            service.db.beerDao().deleteAll()
            beerService.mapOfBeer = mutableMapOf()
        }

    }

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }


}