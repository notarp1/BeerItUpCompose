package com.notarmaso.beeritup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.interfaces.Observable
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class SelectBeverageQuantityViewModel(val s: Service, private val kitchenRepo: KitchenRepository) :
    ViewModel(), Observable {

    private var totalStock: Int = 1

    private var _qtySelected by mutableStateOf(totalStock)
    val qtySelected: Int get() = _qtySelected

    private var _price by mutableStateOf(0f)
    val price: Float get() = _price

    private var _beveragePriceList by mutableStateOf<List<Beverage>?>(listOf())
    val beveragePriceList: List<Beverage>? get() = _beveragePriceList


    private var beveragePurchaseReceiveObj: BeveragePurchaseReceiveObj? = null

    init {
        s.observer.register(this)
    }


    fun incrementCounter() {
        if (_qtySelected >= totalStock) _qtySelected = totalStock
        else _qtySelected++

    }

    fun decrementCounter() {
        if (_qtySelected <= 1) _qtySelected = 1
        else _qtySelected--
    }


    fun getPriceClicked() {
        viewModelScope.launch {
            calculatePrice()
        }
    }

    fun onConfirm() {
        viewModelScope.launch {
            makeTransaction()
        }
    }

    private fun getPricingList() {
        viewModelScope.launch {
            val res: Response<List<Beverage>>

            withContext(Dispatchers.IO) {
                res = kitchenRepo.getBeverageInStock(s.stateHandler.appMode.kId,
                    s.selectedBeverage.beverageTypeId)
            }

            if (res.isSuccessful) {
                res.body()?.let { _beveragePriceList = it }
            }
        }
    }


    private suspend fun calculatePrice() {

        val res: Response<BeveragePurchaseReceiveObj>


        val configObj = BeveragePurchaseConfigObj(s.selectedBeverage.name, qtySelected)
        withContext(Dispatchers.IO) {
            res = kitchenRepo.calculatePrice(s.stateHandler.appMode.kId, configObj)
        }

        if (res.isSuccessful) {
            res.body()?.let {
                _price = it.price.div(100)
                beveragePurchaseReceiveObj = it
            }
            return
        }
        s.makeToast("Error: " + res.message())


    }


    private suspend fun makeTransaction() {
        val bevObj = beveragePurchaseReceiveObj

        if (bevObj != null) {

            //Set drinker ID
            for (beverage: Beverage in bevObj.beverages) {
                beverage.bevDrinkerId = s.stateHandler.appMode.uId
            }

            val res: Response<String>


            withContext(Dispatchers.IO) {
                res = kitchenRepo.acceptTransaction(
                    s.stateHandler.appMode.kId,
                    s.stateHandler.appMode.uId,
                    bevObj.beverages
                )
            }

            if (res.isSuccessful) {
                s.makeToast("Enjoy your beverage!")
                s.navigateAndClearBackstack(Pages.MAIN_MENU)
                _qtySelected = 1

            }else s.makeToast("Errasor: ${res.message()}")


        }

    }

    override fun update(funcToRun: FuncToRun) {
        if (funcToRun == FuncToRun.GET_SPECIFIC_BEV_STOCK) {
            getPricingList()
            totalStock = s.selectedBeverage.stock
        }
    }


}