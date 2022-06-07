package com.notarmaso.db_access_setup.views.beeritup.select_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.BeverageType
import com.notarmaso.db_access_setup.views.beeritup.Category
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectBeverageViewModel(val service: Service,
                              val navController: NavHostController,
                              val stateHandler: StateHandler) : ViewModel() {


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
                res = kitchenRepo.getBeveragesInStock(stateHandler.appMode.kId, selectedCategory)
            }

            handleErrorKitchen(res)


        }
    }

    fun navToNextPage(location: String, beverageType: BeverageType){
        service.setBeverageType(beverageType)
        navController.navigate(location)
    }


    private fun handleErrorKitchen(response: Response<MutableList<BeverageType>>) {

        when (response.code()) {
            200 -> {
                response.body()?.let { _beverageTypes = it }

            }
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Error: Unknown: ${response.message()}")
        }
    }

}