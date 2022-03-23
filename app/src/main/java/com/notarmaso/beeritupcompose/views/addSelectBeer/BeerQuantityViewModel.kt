package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.models.Beer


class BeerQuantityViewModel(val service: Service): ViewModel() {




    var qtySelected by mutableStateOf(1)
    var pricePaid by mutableStateOf("120")
    var pricePerBeer: Float? = null


    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }



    fun incrementCounter(){
        val beerCount = service.selectedGlobalBeer?.count

        if(beerCount != null){
            if(qtySelected >= beerCount) qtySelected = beerCount
            else qtySelected++
        }
    }

    fun decrementCounter(){
        if(qtySelected <= 1) qtySelected = 1
        else qtySelected--
    }

    fun onConfirm(addBeer: Boolean = false){

        if(addBeer) {
            val pricePerBeer = pricePaid.toFloat() / qtySelected.toFloat()
            this.pricePerBeer = pricePerBeer
            service.createAlertBoxAddBeer(pricePerBeer, ::onAccept)
            return
        }

        service.createAlertBoxSelectBeer(qtySelected, 23f, ::onAccept)

    }

    private fun onAccept(){
        val selectedBeer = service.selectedGlobalBeer
        val user = service.serializeUser(service.currentUser)

        for(i in 0..qtySelected){

            if(selectedBeer?.name != null && pricePerBeer != null) {
                service.db.beerDao().insert(Beer(name = selectedBeer.name, price = pricePerBeer!!, owner = user))
            }
        }
    }


}