package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.GlobalBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SelectBeerViewModel(val service: Service) : ViewModel(), ViewModelFunction {

    private var beerList: MutableList<Beer>? = null


    private var carlsberg: MutableList<Beer>? = mutableListOf()
    private var turborg: MutableList<Beer>? = mutableListOf()
    private var classic: MutableList<Beer>? = mutableListOf()
    private var carlsSpecial: MutableList<Beer>? = mutableListOf()
    private var guld: MutableList<Beer>? = mutableListOf()
    private var heineken: MutableList<Beer>? = mutableListOf()
    private var julebryg: MutableList<Beer>? = mutableListOf()
    private var raa: MutableList<Beer>? = mutableListOf()
    private var rClassic: MutableList<Beer>? = mutableListOf()
    private var rExport: MutableList<Beer>? = mutableListOf()
    private var rPilsner: MutableList<Beer>? = mutableListOf()

    var listOfBeers: MutableList<MutableList<Beer>?> = mutableListOf(carlsberg,turborg, classic,
    carlsSpecial, guld, heineken, julebryg, raa, rClassic, rExport, rPilsner)
    init {
        service.beerObs.register(this)
    }



    fun setBeer(globalBeer: GlobalBeer){
        service.selectedGlobalBeer = globalBeer
    }

    private fun getBeers() {

        beerList = null
        for(x in listOfBeers){
            x?.clear()
        }
        beerList = service.db.beerDao().getAll()
    }


    fun getStock(name: String): Int? {


        return when (name) {
            "Carlsberg" -> carlsberg?.count()
            "Tuborg" -> turborg?.count()
            "Classic" -> classic?.count()
            "Carls Special" -> carlsSpecial?.count()
            "Gylden Giz" -> guld?.count()
            "Heineken" -> heineken?.count()
            "Julebryg" -> julebryg?.count()
            "Tuborg Rå" -> raa?.count()
            "Royal Classic" -> rClassic?.count()
            "Royal Export" -> rExport?.count()
            "Royal Pilsner" -> rPilsner?.count()
            else -> 0
        }
    }
    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    /*Should only happen if change has happened */
    override fun update() {
        viewModelScope.launch(Dispatchers.IO){
            getBeers()
            if(beerList != null) {
                for (beer in beerList!!){
                   when(beer.name){
                       "Carlsberg" -> carlsberg?.add(beer)
                       "Tuborg" -> turborg?.add(beer)
                       "Classic" -> classic?.add(beer)
                       "Carls Special" -> carlsSpecial?.add(beer)
                       "Gylden Giz" -> guld?.add(beer)
                       "Heineken" -> heineken?.add(beer)
                       "Julebryg" -> julebryg?.add(beer)
                       "Tuborg Rå" -> raa?.add(beer)
                       "Royal Classic" -> rClassic?.add(beer)
                       "Royal Export" -> rExport?.add(beer)
                       "Royal Pilsner" -> rPilsner?.add(beer)
                   }
                }
            }

        }



    }
}