package com.notarmaso.beeritupcompose.views.addSelectBeer

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.deserializeBeerGroup
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.SampleData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get


class SelectBeerViewModel(val service: Service, val beerService: BeerService) : ViewModel(), ViewModelFunction {

    private val beerRepository: BeerRepository = BeerRepository(service.context)

    init {
        beerService.beerObs.register(this)
    }



    fun setBeer(globalBeer: GlobalBeer){
        service.selectedGlobalBeer = globalBeer
        service.miscObs.notifySubscribers()
    }


    fun getStock(name: String): Int {
        return beerService.getStock(name = name)
    }

    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    /*Should only happen if change has happened */
    override fun update() {

     viewModelScope.launch(Dispatchers.IO) {
            val beerGroups: List<BeerGroup> = beerRepository.getAllBeerGroups()


            for (group in beerGroups) {

                val beers: MutableList<Beer>? = deserializeBeerGroup(beers = group.beers)

                val list = beerService.mapOfBeer[group.groupName]
                list?.clear()
                if (beers != null) {
                    for(x in beers){
                        list?.add(x)
                    }
                }
            }

        }
    }
}


