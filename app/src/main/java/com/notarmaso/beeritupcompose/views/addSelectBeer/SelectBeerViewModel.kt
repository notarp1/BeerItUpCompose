package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.GlobalBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SelectBeerViewModel(val service: Service) : ViewModel(), ViewModelFunction {

    private var beerList: List<Beer>? = null

    init {
        service.beerObs.register(this)
    }



    fun setBeer(globalBeer: GlobalBeer){
        service.selectedGlobalBeer = globalBeer
    }

    private fun getBeers() {
        beerList = service.db.beerDao().getAll()
    }

    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update() {
        viewModelScope.launch(Dispatchers.IO){
            getBeers()
        }
    }
}