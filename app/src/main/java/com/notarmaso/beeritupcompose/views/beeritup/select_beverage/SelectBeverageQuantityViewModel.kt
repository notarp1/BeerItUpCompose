package com.notarmaso.db_access_setup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.models.Beverage
import com.notarmaso.beeritupcompose.models.BeveragePurchaseConfigObj
import com.notarmaso.beeritupcompose.models.BeveragePurchaseReceiveObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageQuantityViewModel(val s: Service) : ViewModel() {

    private var totalStock: Int = 1

    private var _qtySelected by mutableStateOf(totalStock)
    val qtySelected: Int get() = _qtySelected

    private val kitchenRepo = KitchenRepository

    fun setup(stock: Int){
        totalStock = stock
    }

    fun incrementCounter(){
        if(_qtySelected >= totalStock) _qtySelected = totalStock
        else _qtySelected++

    }

    fun decrementCounter(){
        if(_qtySelected <= 1) _qtySelected = 1
        else _qtySelected--
    }



    fun onConfirm(){

        viewModelScope.launch {
            val res: Response<BeveragePurchaseReceiveObj>

            withContext(Dispatchers.IO){

                val configObj = BeveragePurchaseConfigObj(s.selectedBeverage.name, qtySelected)
                res = kitchenRepo.calculatePrice(s.stateHandler.appMode.kId, configObj)
            }
            handleError(res)

        }

    }

    private fun handleOnAccept(bevObj: BeveragePurchaseReceiveObj){
        val price: Float = bevObj.price/100

        for (beverage: Beverage in bevObj.beverages){
            beverage.bevDrinkerId = s.stateHandler.appMode.uId
        }


       viewModelScope.launch(Dispatchers.Main) {
            val res: Response<String>

            withContext(Dispatchers.IO){
                res = kitchenRepo.acceptTransaction(s.stateHandler.appMode.kId, s.stateHandler.appMode.uId, bevObj.beverages)
            }

           when (res.code()) {
               200 -> {
                   s.makeToast("Enjoy your beverage(s)!")
                   s.navigateAndClearBackstack(Pages.MAIN_MENU)
                   _qtySelected = 1
               }
               406 -> s.makeToast("Kitchen ID not found: Try re-login")
               500 -> s.makeToast(res.message())
               else -> s.makeToast("Error: Unknown: ${res.message()}")
           }
        }
    }

    private fun handleError(response:Response<BeveragePurchaseReceiveObj>) {
        when (response.code()) {
            200 -> response.body()?.let { handleOnAccept(it) }
            406 -> s.makeToast("Kitchen ID not found: Try re-login")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown: ${response.message()}")
        }
    }


}