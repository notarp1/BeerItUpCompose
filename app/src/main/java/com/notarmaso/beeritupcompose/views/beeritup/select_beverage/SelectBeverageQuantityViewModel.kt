package com.notarmaso.db_access_setup.views.beeritup.select_beverage

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
import com.notarmaso.db_access_setup.models.Beverage
import com.notarmaso.db_access_setup.models.BeveragePurchaseConfigObj
import com.notarmaso.db_access_setup.models.BeveragePurchaseReceiveObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageQuantityViewModel(val navController: NavHostController, val service: Service, val stateHandler: StateHandler) : ViewModel() {

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

                val configObj = BeveragePurchaseConfigObj(service.selectedBeverage.name, qtySelected)
                res = kitchenRepo.calculatePrice(stateHandler.appMode.kId, configObj)
            }
            handleError(res)

        }

    }

    private fun handleOnAccept(bevObj: BeveragePurchaseReceiveObj){
        val price: Float = bevObj.price/100

        for (beverage: Beverage in bevObj.beverages){
            beverage.bevDrinkerId = stateHandler.appMode.uId
        }


       viewModelScope.launch(Dispatchers.Main) {
            val res: Response<String>

            withContext(Dispatchers.IO){
                res = kitchenRepo.acceptTransaction(stateHandler.appMode.kId,stateHandler.appMode.uId, bevObj.beverages)
            }

           when (res.code()) {
               200 -> {
                   service.makeToast("Enjoy your beverage(s)!")
                   navController.navigate(MainActivity.MAIN_MENU){
                       popUpTo(MainActivity.SELECT_BEVERAGE_2){
                           inclusive=true
                       }}
                   _qtySelected = 1
               }
               406 -> service.makeToast("Kitchen ID not found: Try re-login")
               500 -> service.makeToast(res.message())
               else -> service.makeToast("Error: Unknown: ${res.message()}")
           }
        }
    }

    private fun handleError(response:Response<BeveragePurchaseReceiveObj>) {
        when (response.code()) {
            200 -> response.body()?.let { handleOnAccept(it) }
            406 -> service.makeToast("Kitchen ID not found: Try re-login")
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Error: Unknown: ${response.message()}")
        }
    }


}