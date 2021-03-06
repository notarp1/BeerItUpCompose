package com.notarmaso.beeritup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Category
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.interfaces.Observable
import com.notarmaso.beeritup.models.BeverageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageViewModel(val s: Service, private val kitchenRepo: KitchenRepository) : ViewModel(), Observable {



    private var _beveragesInStock by mutableStateOf<List<BeverageType>>(listOf())
    val beveragesInStock: List<BeverageType> get() = _beveragesInStock


    private var _selectedCategory by mutableStateOf(Category.BEERS)
    val selectedCategory: String get() = _selectedCategory.category

    init {
        s.observer.register(this)
    }

    fun setCategory(category: Category) {
        _selectedCategory = category
        getBeveragesInStock()
    }

    var hasRun: Boolean = false

    private fun getBeveragesInStock(){
        viewModelScope.launch {
            val res: Response<List<BeverageType>>

            withContext(Dispatchers.IO){
                res = kitchenRepo.getBeveragesInStock(s.stateHandler.appMode.kId, selectedCategory)
            }

            if(res.isSuccessful){
                res.body()?.let { _beveragesInStock = it }
            }else s.makeToast("Error: " + res.message())
        }
    }

    fun navToNextPage(location: Pages, beverageType: BeverageType){
        s.setBeverageType(beverageType)
        s.observer.notifySubscribers(FuncToRun.GET_SPECIFIC_BEV_STOCK)
        s.navigate(location)
    }



    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_BEVERAGES_IN_STOCK){
            getBeveragesInStock()
        }
    }

}