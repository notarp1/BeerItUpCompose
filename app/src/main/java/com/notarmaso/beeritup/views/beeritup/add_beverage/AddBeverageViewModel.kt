package com.notarmaso.beeritup.views.beeritup.add_beverage

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

class AddBeverageViewModel(val s: Service, private val kitchenRepo: KitchenRepository) : ViewModel(), Observable {

    private var _beverageTypes by mutableStateOf<List<BeverageType>>(listOf())
    val beverageTypes: List<BeverageType> get() = _beverageTypes


    private var _selectedCategory by mutableStateOf(Category.BEERS)
    val selectedCategory: String get() = _selectedCategory.category


    init {
        s.observer.register(this)
    }

    fun setCategory(category: Category) {
        _selectedCategory = category
        getBeverageTypes()
    }


    //DELETE THIS
    var hasrun: Boolean = false


    fun getBeverageTypes(){
        viewModelScope.launch {
            val res: Response<MutableList<BeverageType>>
            withContext(Dispatchers.IO){
                val kitchenId: Int = s.stateHandler.appMode.kId

                res = kitchenRepo.getBeverageTypes(kitchenId, selectedCategory)


            }
            handleErrorKitchen(res)
        }
    }

    fun navToNextPage(location: Pages, beverageType: BeverageType){
        s.setBeverageType(beverageType)
        s.navigate(location)
    }


    private fun handleErrorKitchen(response: Response<MutableList<BeverageType>>) {
        println(response.code())
        when (response.code()) {

            200 -> {
                response.body()?.let { _beverageTypes = it }
            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Unknown error ${response.code()}: ${response.message()}")
        }
    }

    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_BEVERAGE_TYPES){
            getBeverageTypes()
        }
    }


}