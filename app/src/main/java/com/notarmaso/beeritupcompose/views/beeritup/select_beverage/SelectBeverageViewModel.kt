package com.notarmaso.db_access_setup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Category
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.models.BeverageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageViewModel(val s: Service) : ViewModel() {


    private val kitchenRepo = KitchenRepository

    private var _beverageTypes by mutableStateOf<List<BeverageType>>(listOf())
    val beverageTypes: List<BeverageType> get() = _beverageTypes




    private var _selectedCategory by mutableStateOf(Category.BEERS)
    val selectedCategory: String get() = _selectedCategory.category

    fun setCategory(category: Category) {
        _selectedCategory = category
        getBeverageTypes()
    }

    var hasRun: Boolean = false

    fun getBeverageTypes(){
        viewModelScope.launch {
            val res: Response<MutableList<BeverageType>>

            withContext(Dispatchers.IO){
                res = kitchenRepo.getBeveragesInStock(s.stateHandler.appMode.kId, selectedCategory)
            }

            handleErrorKitchen(res)


        }
    }

    fun navToNextPage(location: Pages, beverageType: BeverageType){
        s.setBeverageType(beverageType)
        s.navigate(location)
    }


    private fun handleErrorKitchen(response: Response<MutableList<BeverageType>>) {

        when (response.code()) {
            200 -> {
                response.body()?.let { _beverageTypes = it }

            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown: ${response.message()}")
        }
    }

}