package com.notarmaso.db_access_setup.views.beeritup.add_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.models.BeverageDBEntryObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddBeverageQuantityViewModel(val s: Service) : ViewModel() {



    private var _qtySelected by mutableStateOf(24)
    val qtySelected: Int get() = _qtySelected

    private var _pricePaid by mutableStateOf("120")
    val pricePaid: String get() = _pricePaid

    private val kitchenRep = KitchenRepository




    fun incrementCounter(){
        if(_qtySelected >= 48) _qtySelected = 48
        else _qtySelected++

    }

    fun decrementCounter(){
        if(_qtySelected <= 1) _qtySelected = 1
        else _qtySelected--
    }

    fun setPricePaidTxt(string: String){
        if(string.length >= 4){
            return
        }
        _pricePaid = string
    }


    fun onConfirm(){

        viewModelScope.launch {
            val priceToInt = (pricePaid.toFloat() * 100).toInt()
            val pricePerBeer = priceToInt/(qtySelected)
            val selectedBeer = s.selectedBeverage.name

            val bevEntryObj = BeverageDBEntryObject(qtySelected, pricePerBeer, s.stateHandler.appMode.uId)

            val res: Response<String>

            withContext(Dispatchers.IO){
                res = kitchenRep.addBeverages(bevEntryObj, s.stateHandler.appMode.kId, selectedBeer)
            }
            handleError(res)

        }

    }

    private fun handleError(response: Response<String>) {

        when (response.code()) {
            200 -> {

               viewModelScope.launch(Dispatchers.Main) {
                   s.makeToast("Added Beers")
                   s.navigateAndClearBackstack(Pages.MAIN_MENU)
               }
                _qtySelected = 24


            }
            406 ->{
                viewModelScope.launch {
                  s.makeToast("Kitchen ID not found: Try re-login")
                }


            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown: ${response.message()}")
        }
    }


}