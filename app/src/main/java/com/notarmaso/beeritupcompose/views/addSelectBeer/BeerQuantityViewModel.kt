package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BeerQuantityViewModel(val service: Service, val beerService: BeerService): ViewModel(), ViewModelFunction {




    var qtySelected by mutableStateOf(1)
    var pricePaid by mutableStateOf("120")
    var pricePerBeer: Float? = null
    var beerCount by mutableStateOf(0)
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
        qtySelected = if(service.currentPage == MainActivity.ADD_BEER) 24 else 1

    }


    fun incrementCounter(){

        if(service.currentPage == MainActivity.SELECT_BEER){
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
        val mapOfBeer = beerService.mapOfBeer[selectedBeer?.name]

        if(selectedBeer != null) {
            when(service.currentPage){
                MainActivity.ADD_BEER -> handleAddBeer(selectedBeer, mapOfBeer)
                MainActivity.SELECT_BEER -> handleSelectBeer(selectedBeer, mapOfBeer)
            }
        }


    }

    private fun handleAddBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>?,
    ) {
        val user = service.currentUser?.serializeUser()


        viewModelScope.launch(Dispatchers.IO) {

            for (i in 0 until qtySelected) {
                if (pricePerBeer != null) {
                    val beer = Beer(name = selectedBeer.name,
                        price = pricePerBeer!!,
                        owner = user!!)
                    mapOfBeer?.add(beer)
                }
            }

            /* Update BeerGroup  STRING STRING */
            val beerGroup = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))

            service.db.beerDao().updateBeerGroup(beerGroup)
        }
    }


    private fun handleSelectBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>?

    ) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = service.currentUser
            val list = currentUser?.owesTo

            val owedToList: MutableMap<String, Float>? =
                if (list != null) currentUser.owesTo?.fromJsonToList() else mutableMapOf()


            var beer: Beer?
            var beerOwner: User?
            var price: Float?
            var prevBeerOwner: String? = mapOfBeer?.get(0)?.owner


            for (i in 0 until qtySelected) {

                beer = mapOfBeer?.removeFirstOrNull()

                if (beer != null) {
                    beerOwner = beer.owner.deserializeUser()

                    price = beer.price

                    val owedFromList: MutableMap<String, Float>? =
                        if (list != null) beerOwner.owedFrom?.fromJsonToList() else mutableMapOf()

                    if (owedToList != null) setPayments(owedToList, beerOwner, price)
                    if (owedFromList != null) setPayments(owedFromList, currentUser, price)

                    beerOwner.owedFrom = owedFromList?.fromListToJson()


                    if (i == qtySelected - 1 || prevBeerOwner != beer.owner) {

                        if (prevBeerOwner != null) {
                            service.db.userDao().updateUser(prevBeerOwner.deserializeUser())
                        }

                        prevBeerOwner = beer.owner
                    }

                }
            }
            currentUser?.owesTo = owedToList?.fromListToJson()

            if (currentUser != null) {
                service.db.userDao().updateUser(currentUser)
            }

            val beerGroupToUpdate = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))

           /* val beerToUpdate: BeerGroup =
                selectedBeer.name.let { service.db.beerDao().getAllFromGroup(it) }
            beerToUpdate.beers = serializeBeerGroup(mapOfBeer)*/

            service.db.beerDao().updateBeerGroup(beerGroupToUpdate)

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

    fun setTotalQty(selectedBeer: String) {
        beerCount = beerService.getStock(selectedBeer)!!
    }


}
















