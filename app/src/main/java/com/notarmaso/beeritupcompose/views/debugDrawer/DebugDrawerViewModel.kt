package com.notarmaso.beeritupcompose.views.debugDrawer

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DebugDrawerViewModel(val service: Service, private val beerService: BeerService): ViewModel() {
    private val beerRepository: BeerRepository = BeerRepository(service.context)
    private val userRepository: UserRepository = UserRepository(service.context)

    fun removeUsers() {
        viewModelScope.launch(Dispatchers.IO){
          userRepository.deleteAll()
        }

    }

    fun removeBeers() {
        viewModelScope.launch(Dispatchers.IO){
            beerRepository.deleteAll()
            for ((key, value) in beerService.mapOfBeer.entries) {
              value.clear()
            }
        }
    }

    fun resetUsers(){

        val owedFrom: MutableMap<String, Float> = mutableStateMapOf()
        val owesTo: MutableMap<String, Float> = mutableStateMapOf()
        val totalBeers: MutableMap<String, Int> = mutableStateMapOf(
            "TOTAL" to 0,
            "JANUARY" to 0,
            "FEBRUARY" to 0,
            "MARCH" to 0,
            "APRIL" to 0,
            "MAY" to 0,
            "JUNE" to 0,
            "JULY" to 0,
            "AUGUST" to 0,
            "SEPTEMBER" to 0,
            "OCTOBER" to 0,
            "NOVEMBER" to 0,
            "DECEMBER" to 0
        )

        val user1 = User("MadsSell", "22334455", owedFrom.fromListFloatToJson(), owesTo.fromListFloatToJson(),totalBeers.fromListIntToJson())
        val user2 = User("HenrietteSell", "22334455", owedFrom.fromListFloatToJson(), owesTo.fromListFloatToJson(),totalBeers.fromListIntToJson())
        val user3 = User("ArmadilloBuy", "22334455", owedFrom.fromListFloatToJson(), owesTo.fromListFloatToJson(),totalBeers.fromListIntToJson())
        val user4 = User("FerrariBuy", "22334455", owedFrom.fromListFloatToJson(), owesTo.fromListFloatToJson(),totalBeers.fromListIntToJson())

        viewModelScope.launch(Dispatchers.IO){
            userRepository.insertUser(user1)
            userRepository.insertUser(user2)
            userRepository.insertUser(user3)
            userRepository.insertUser(user4)


            val mapOfBeer = beerService.mapOfBeer["Carlsberg"]
            for (i in 0 until 4) {

                val beer = Beer(name = "Carlsberg",
                    price = 5.0f,
                    owner = user1.name)
                mapOfBeer?.add(beer)
            }
            for (i in 0 until 6) {

                val beer = Beer(name = "Carlsberg",
                    price = 5.0f,
                    owner = user2.name)
                mapOfBeer?.add(beer)
            }
            val beerGroup = BeerGroup("Carlsberg", serializeBeerGroup(mapOfBeer))

            beerRepository.updateBeerGroup(beerGroup)
            service.observer.notifySubscribers(MainActivity.BUY_BEER)

        }


    }

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }


}