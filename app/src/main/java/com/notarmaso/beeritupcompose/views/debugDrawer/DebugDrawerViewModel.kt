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

        val owesList: MutableMap<String, Float> = mutableStateMapOf()
        val log: MutableList<String> = mutableListOf()

        val owesJson = owesList.fromListFloatToJson()
        val logJson = log.fromListToJson()
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

        val user1 = User("OGLasse", "29659818", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user2 = User("Benjamin_T64", "27116223", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user3 = User("Sebastian_T48", "61406035", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user4 = User("Christian_T66", "22270704", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user5 = User("Jonas_S40", "23394505", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user6 = User("Mathilde_T52", "28774814", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user7 = User("Fiskefrede69", "30366290", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user8 = User("Lasse_T46", "22508087", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user9 = User("Marcus_T62", "42413928", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user10 = User("Tomas_T56", "52782357", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user11 = User("Tiller", "30330321", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user12 = User("Maiken_S32", "31167262", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user13 = User("Mathias_S22", "30577607", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user14 = User("Jakob_S16", "23664152", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user15 = User("Marie_T50", "60681667", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user16 = User("Juliane_S30", "61664157", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        val user17 = User("FrederikDenFørste", "26228814", owesJson, owesJson ,totalBeers.fromListIntToJson(), 0f, 0f, logJson,logJson,logJson)
        setBeer(user1, 139)
        setBeer(user2, 126)
        setBeer(user3, 81)
        setBeer(user4, 72)
        setBeer(user5, 57)
        setBeer(user6, 56)
        setBeer(user7, 53)
        setBeer(user8, 52)
        setBeer(user9, 48)
        setBeer(user10, 29)
        setBeer(user11, 17)
        setBeer(user12, 16)
        setBeer(user13, 8)
        setBeer(user14, 12)
        setBeer(user15, 3)
        setBeer(user16, 0)
        setBeer(user17, 15)

        /*Sebastian*/
        val seb = user3.owesTo.fromJsonToListFloat()
        val seb2 = user3.owedFrom.fromJsonToListFloat()
        seb[user7.name] = 9.42f
        seb[user8.name] = 62.24f
        seb[user6.name] = 7.78f
        seb[user1.name] = 82.51f


        seb2[user8.name] = 7.78f
        seb2[user13.name] = 3.89f
        seb2[user1.name] = 7.78f

        user3.owesTo = seb.fromListFloatToJson()
        user3.owedFrom = seb.fromListFloatToJson()
        /*Mathilde */
        val matil = user6.owesTo.fromJsonToListFloat()
        val matil2 = user6.owedFrom.fromJsonToListFloat()
        matil[user2.name] = 9.42f
        matil[user1.name] = 3.89f

        matil2[user2.name] = 70.02f
        matil2[user7.name] = 11.67f
        matil2[user5.name] = 46.68f
        matil2[user9.name] = 23.34f
        matil2[user3.name] = 7.78f
        matil2[user11.name] = 7.78f
        matil2[user10.name] = 46.68f


        user6.owesTo = matil.fromListFloatToJson()
        user6.owedFrom = matil2.fromListFloatToJson()

        /*Fiskefrede */
        val listfisk = user7.owesTo.fromJsonToListFloat()
        val listfisk2 = user7.owedFrom.fromJsonToListFloat()
        listfisk[user2.name] = 9.42f
        listfisk[user4.name] = 4.71f
        listfisk[user8.name] = 46.68f
        listfisk[user6.name] = 11.67f
        listfisk[user1.name] = 21.09f

        listfisk2[user5.name] = 14.13f
        listfisk2[user12.name] = 4.71f
        listfisk2[user9.name] = 18.84f
        listfisk2[user3.name] = 9.42f
        listfisk2[user11.name] = 9.42f
        listfisk2[user10.name] = 4.71f

        user7.owesTo = listfisk.fromListFloatToJson()
        user7.owedFrom = listfisk2.fromListFloatToJson()

        /*Frederikdenførste */
        val list17 = user15.owesTo.fromJsonToListFloat()
        list17[user8.name] = 35.01f
        list17[user1.name] =23.34f

        user17.owesTo = list17.fromListFloatToJson()

        /*Marie */
        val list15 = user15.owesTo.fromJsonToListFloat()
        list15[user8.name] = 3.89f
        list15[user1.name] = 9.42f

        user15.owesTo = list15.fromListFloatToJson()

        /*Tomas */
        val listTi = user10.owesTo.fromJsonToListFloat()
        listTi[user7.name] = 4.71f
        listTi[user6.name] = 46.68f
        user10.owesTo = listTi.fromListFloatToJson()


        /*Tiller */
        val listTiller = user11.owesTo.fromJsonToListFloat()
        listTiller[user2.name] = 4.19f
        listTiller[user7.name] = 9.42f
        listTiller[user8.name] = 27.23f
        listTiller[user6.name] = 7.78f
        listTiller[user1.name] = 22.20f

        user11.owesTo = listTiller.fromListFloatToJson()


        /*Jonas_S40 */
        val list5 = user5.owesTo.fromJsonToListFloat()
        list5[user7.name] = 14.13f
        list5[user8.name] = 97.25f
        list5[user6.name] = 46.68f
        list5[user1.name] = 19.45f

        user5.owesTo = list5.fromListFloatToJson()

        /*Jakob S16 */
        val list14 = user14.owesTo.fromJsonToListFloat()
        list14[user1.name] = 44.64f
        list14[user2.name] = 9.42f
        user14.owesTo = list14.fromListFloatToJson()

        /*Maiken_S32 */
        val list12 = user12.owesTo.fromJsonToListFloat()
        list12[user7.name] = 4.71f
        list12[user8.name] = 15.56f
        list12[user1.name] = 45.25f

        user12.owesTo = list12.fromListFloatToJson()

        /*Lasse_T46*/
        val list8 = user8.owesTo.fromJsonToListFloat()
        val list81 = user8.owedFrom.fromJsonToListFloat()
        list8[user3.name] = 7.78f

        list81[user2.name] = 3.89f
        list81[user7.name] = 46.68f
        list81[user5.name] = 97.25f
        list81[user12.name] = 15.56f
        list81[user9.name] = 23.34f
        list81[user15.name] = 3.89f
        list81[user1.name] = 15.56f
        list81[user3.name] = 62.24f
        list81[user11.name] = 27.23f

        user8.owesTo = list8.fromListFloatToJson()
        user8.owedFrom = list81.fromListFloatToJson()

        /*Mathias_S22*/
        val list13 = user13.owesTo.fromJsonToListFloat()
        list13[user3.name] = 3.89f

        user13.owesTo = list13.fromListFloatToJson()

        /*Marcus_T62*/
        val list9 = user9.owesTo.fromJsonToListFloat()
        list9[user2.name] = 14.13f
        list9[user7.name] = 18.84f
        list9[user8.name] = 23.34f
        list9[user6.name] = 23.34f
        list9[user1.name] = 120.13f
        user9.owesTo = list9.fromListFloatToJson()

        /*Benjamin_T64 */
        val list2 = user2.owesTo.fromJsonToListFloat()
        val list21 = user2.owedFrom.fromJsonToListFloat()
        list2[user8.name] = 3.89f
        list2[user6.name] = 70.02f
        list2[user1.name] = 35.01f


        list21[user14.name] = 9.42f
        list21[user7.name] = 9.42f
        list21[user9.name] = 14.13f
        list21[user6.name] = 9.42f
        list21[user11.name] = 4.71f

        user2.owesTo = list2.fromListFloatToJson()
        user2.owedFrom = list21.fromListFloatToJson()
        /*Christian_T66 */
        val list4 = user4.owesTo.fromJsonToListFloat()

        list4[user1.name] = 19.45f
        list4[user7.name] = 4.71f
        user4.owesTo = list4.fromListFloatToJson()

        /*OGLasse*/
        val list1 = user1.owesTo.fromJsonToListFloat()
        val list11 = user1.owedFrom.fromJsonToListFloat()
        list1[user8.name] = 15.56f
        list1[user3.name] = 7.78f


        list11[user2.name] = 35.1f
        list11[user4.name] = 19.45f
        list11[user7.name] = 21.09f
        list11[user14.name] = 44.64f
        list11[user5.name] = 19.45f
        list11[user12.name] = 45.25f
        list11[user9.name] = 120.13f
        list11[user15.name] = 9.42f
        list11[user6.name] = 3.89f
        list11[user3.name] = 82.51f
        list11[user11.name] = 22.20f

        user1.owesTo = list1.fromListFloatToJson()
        user1.owedFrom = list11.fromListFloatToJson()

        viewModelScope.launch(Dispatchers.IO){
            userRepository.insertUser(user1)
            userRepository.insertUser(user2)
            userRepository.insertUser(user3)
            userRepository.insertUser(user4)
            userRepository.insertUser(user5)
            userRepository.insertUser(user6)
            userRepository.insertUser(user7)
            userRepository.insertUser(user8)
            userRepository.insertUser(user9)
            userRepository.insertUser(user10)
            userRepository.insertUser(user11)
            userRepository.insertUser(user12)
            userRepository.insertUser(user13)
            userRepository.insertUser(user14)
            userRepository.insertUser(user15)
            userRepository.insertUser(user16)
            userRepository.insertUser(user17)



            val mapOfBeerTuborg = beerService.mapOfBeer["Tuborg"]
            val mapOfBeerClassic = beerService.mapOfBeer["Classic"]
            val mapOfBeerRC = beerService.mapOfBeer["Royal Classic"]
            val mapOfBeerRP = beerService.mapOfBeer["Royal Pilsner"]
            for (i in 0 until 94) {

                val beer = Beer(name = "Tuborg",
                    price = 3.89f,
                    owner = user3.name)
                mapOfBeerTuborg?.add(beer)
            }
            for (i in 0 until 19) {

                val beer = Beer(name = "Classic",
                    price = 3.89f,
                    owner = user1.name)
                mapOfBeerClassic?.add(beer)
            }
            for (i in 0 until 2) {

                val beer = Beer(name = "Royal Classic",
                    price = 4.71f,
                    owner = user1.name)
                mapOfBeerRC?.add(beer)
            }

            val beer = Beer(name = "Royal Pilsner",
                price = 4.71f,
                owner = user7.name)
            mapOfBeerRP?.add(beer)

            val beerGroup1 = BeerGroup("Tuborg", serializeBeerGroup(mapOfBeerTuborg))
            val beerGroup2 = BeerGroup("Classic", serializeBeerGroup(mapOfBeerClassic))
            val beerGroup3 = BeerGroup("Royal Classic", serializeBeerGroup(mapOfBeerRC))
            val beerGroup4 = BeerGroup("Royal Pilsner", serializeBeerGroup(mapOfBeerRP))

            beerRepository.updateBeerGroup(beerGroup1)
            beerRepository.updateBeerGroup(beerGroup2)
            beerRepository.updateBeerGroup(beerGroup3)
            beerRepository.updateBeerGroup(beerGroup4)
            service.observer.notifySubscribers(MainActivity.BUY_BEER)

        }


    }

    private fun setBeer(user: User, count:Int) {
        val beer = user.totalBeers.fromJsonToListInt()
        beer["TOTAL"] = count
        user.totalBeers = beer.fromListIntToJson()
    }

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }


}