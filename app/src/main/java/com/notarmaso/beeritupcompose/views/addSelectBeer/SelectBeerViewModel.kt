package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.deserializeBeerGroup
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.GlobalBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SelectBeerViewModel(val service: Service, private val beerService: BeerService) : ViewModel(), ViewModelFunction {

    private val beerRepository: BeerRepository = BeerRepository(service.context)

    init {
        service.observer.register(this)
    }



    fun setBeer(globalBeer: GlobalBeer){
        service.selectedGlobalBeer = globalBeer

        if(service.currentPage == MainActivity.BUY_BEER) service.observer.notifySubscribers(MainActivity.BUY_BEER)
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
    override fun update(page: String) {
    if(isAddingBeer(page) || isBuyingBeer(page) || page == "UPDATE_BEER_LIST"){

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

    private fun isAddingBeer(page: String): Boolean{
        return page == MainActivity.ADD_BEER
    }

    private fun isBuyingBeer(page: String): Boolean{
        return page == MainActivity.BUY_BEER
    }
}


