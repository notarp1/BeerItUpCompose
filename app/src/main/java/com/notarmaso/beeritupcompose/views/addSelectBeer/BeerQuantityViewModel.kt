package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BeerQuantityViewModel(val service: Service, val beerService: BeerService): ViewModel(), ViewModelFunction {



    val gson = Gson()
    var qtySelected by mutableStateOf(1)
    var pricePaid by mutableStateOf("120")
    var pricePerBeer: Float? = null

    init {
        beerService.beerObs.register(this)
    }

    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update() {
        qtySelected = if(service.currentPage == MainActivity.IS_ADDING_BEER) 24 else 1
    }


    fun incrementCounter(){
        val beerCount = service.selectedGlobalBeer?.count

        if(beerCount != null && service.currentPage != MainActivity.IS_ADDING_BEER){
            if(qtySelected >= beerCount) qtySelected = beerCount
            else qtySelected++
        } else {
            if(qtySelected >= 24) qtySelected = 24
            else qtySelected++
        }
    }

    fun decrementCounter(){
        if(qtySelected <= 1) qtySelected = 1
        else qtySelected--
    }

    fun onConfirm(isAddingBeer: Boolean = false){

        if(isAddingBeer) {
            val pricePerBeer = pricePaid.toFloat() / qtySelected.toFloat()
            this.pricePerBeer = pricePerBeer
            service.createAlertBoxAddBeer(pricePerBeer, ::onAccept, qtySelected)
            return
        }

        service.createAlertBoxSelectBeer(qtySelected, 23f, ::onAccept)

    }

    private fun onAccept() {
        val selectedBeer = service.selectedGlobalBeer

        val mapOfBeer = beerService.mapOfBeer

        /* Add beer */
        if (service.currentPage == MainActivity.IS_ADDING_BEER) {
            val user = service.serializeUser(service.currentUser)

            viewModelScope.launch(Dispatchers.IO) {
                for (i in 0 until qtySelected) {
                    if (selectedBeer?.name != null && pricePerBeer != null) {
                        val beer = Beer(name = selectedBeer.name,
                        price = pricePerBeer!!,
                        owner = user)
                        mapOfBeer[selectedBeer.name]?.add(beer)
                    }
                }
                /* Update BeerGroup  STRING STRING */
                val beerGroup = selectedBeer?.name?.let { BeerGroup(it, beerService.serializeBeerGroup(mapOfBeer[selectedBeer.name])) }

                if (beerGroup != null) {
                    service.db.beerDao().updateBeerGroup(beerGroup)
                }


            }
        }
        /*Selecting Beer*/
        else {

            val currentUser = service.currentUser

            val owedToList: MutableMap<String, Float> = try {
                getOwned(currentUser?.owesTo)
            } catch (e: Exception) {
                mutableMapOf()
            }

            var beer: Beer?
            var beerOwner: User?
            var price: Float?

            for(i in 0 until qtySelected){

                beer = mapOfBeer[selectedBeer?.name]?.removeFirstOrNull()

                if(beer != null) {
                    beerOwner = beer.owner.let { service.deserializeUser(it) }
                    price = beer.price


                    val owedFromList = getOwedTo(beerOwner.owedFrom)

                    setPayments(owedToList, beerOwner, price)
                    setPayments(owedFromList, currentUser, price)
                }
            }
        }
    }

    private fun setPayments(
        paymentList: MutableMap<String, Float>,
        customerToUpdate: User?,
        priceToUpdate: Float,
    ) {
        if (!paymentList.containsKey(customerToUpdate?.name)) customerToUpdate?.name?.let { paymentList.put(it, priceToUpdate) }
        else {
            customerToUpdate?.name?.let {
                val prevPrice = paymentList[it]

                if (prevPrice != null) {
                    paymentList[it] = prevPrice + priceToUpdate
                }
            }
        }
    }


    fun getOwned(it:String?): MutableMap<String, Float>{

        val itemType = object : TypeToken<MutableMap<String, Float?>>() {}.type

        return gson.fromJson(it, itemType)
    }

    fun getOwedTo(it:String?): MutableMap<String, Float>{

        val itemType = object : TypeToken<MutableMap<String, Float?>>() {}.type

        return gson.fromJson(it, itemType)
    }

    fun serializeBeer(it: Beer?): String{
        return  gson.toJson(it)
    }
    fun deserializeBeer(it: String): Beer{
        return gson.fromJson(it, Beer::class.java)
    }


}
















