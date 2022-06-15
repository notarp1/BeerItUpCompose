package com.notarmaso.db_access_setup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.models.Beverage
import com.notarmaso.beeritup.models.BeveragePurchaseConfigObj
import com.notarmaso.beeritup.models.BeveragePurchaseReceiveObj
import com.notarmaso.beeritup.models.LeaderboardEntryObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageQuantityViewModel(val s: Service, private val kitchenRepo: KitchenRepository) : ViewModel() {

    private var totalStock: Int = 1

    private var _qtySelected by mutableStateOf(totalStock)
    val qtySelected: Int get() = _qtySelected

    private var _price by mutableStateOf(0f)
    val price: Float get() = _price

    private var _beveragePriceList by mutableStateOf<List<Beverage>?>(listOf())
    val beveragePriceList: List<Beverage>? get() = _beveragePriceList


    private var beveragePurchaseReceiveObj: BeveragePurchaseReceiveObj? = null

    fun setup(stock: Int) {
        totalStock = stock
    }

    fun incrementCounter() {
        if (_qtySelected >= totalStock) _qtySelected = totalStock
        else _qtySelected++

    }

    fun decrementCounter() {
        if (_qtySelected <= 1) _qtySelected = 1
        else _qtySelected--
    }


    fun onClick(){
        viewModelScope.launch {

            calculatePrice()
        }
    }

    fun onConfirm(){
        viewModelScope.launch {
            makeTransaction()
        }
    }

    fun getPricingList(){
        viewModelScope.launch {


        }
    }


    private suspend fun calculatePrice() {

        val res: Response<BeveragePurchaseReceiveObj>

        withContext(Dispatchers.IO) {

            val configObj = BeveragePurchaseConfigObj(s.selectedBeverage.name, qtySelected)
            res = kitchenRepo.calculatePrice(s.stateHandler.appMode.kId, configObj)

            if(handleError(res)) {
                res.body()?.let {
                    _price = it.price.div(100)
                    beveragePurchaseReceiveObj = it
                }
            }
        }

    }

    private fun handleError(response: Response<BeveragePurchaseReceiveObj>): Boolean{

        when (response.code()) {
            200 -> return true
            406 -> s.makeToast("Kitchen ID not found: Try re-login")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown: ${response.message()}")
        }
        return false
    }

    private suspend fun makeTransaction() {
        val bevObj = beveragePurchaseReceiveObj

        if (bevObj != null) {
            for (beverage: Beverage in bevObj.beverages) {
                beverage.bevDrinkerId = s.stateHandler.appMode.uId
            }


            viewModelScope.launch(Dispatchers.Main) {
                val res: Response<String>

                withContext(Dispatchers.IO) {
                    res = kitchenRepo.acceptTransaction(
                        s.stateHandler.appMode.kId,
                        s.stateHandler.appMode.uId,
                        bevObj.beverages
                    )
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
    }


}