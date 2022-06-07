package com.notarmaso.db_access_setup.views.beeritup.add_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.MainActivity
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.BeverageDBEntryObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddBeverageQuantityViewModel(val navController: NavHostController, val service: Service, val stateHandler: StateHandler) : ViewModel() {



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
            val selectedBeer = service.selectedBeverage.name

            val bevEntryObj = BeverageDBEntryObject(qtySelected, pricePerBeer, stateHandler.appMode.uId)

            val res: Response<String>

            withContext(Dispatchers.IO){
                res = kitchenRep.addBeverages(bevEntryObj, stateHandler.appMode.kId, selectedBeer)
            }
            handleError(res)

        }

    }

    private fun handleError(response: Response<String>) {

        when (response.code()) {
            200 -> {

               viewModelScope.launch(Dispatchers.Main) {
                   service.makeToast("Added Beers")

                   navController.navigate(MainActivity.MAIN_MENU){
                       popUpTo(MainActivity.ADD_BEVERAGE_1){
                           inclusive=true
                       }}

               }

                _qtySelected = 24


            }
            406 ->{
                viewModelScope.launch {
                  service.makeToast("Kitchen ID not found: Try re-login")
                }


            }
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Error: Unknown: ${response.message()}")
        }
    }


}