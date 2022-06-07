package com.notarmaso.db_access_setup.views.beeritup.add_beverage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.BeverageType
import com.notarmaso.db_access_setup.views.beeritup.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddBeverageViewModel(val navController: NavHostController, val service: Service, val stateHandler: StateHandler) : ViewModel() {
    private val kitchenRepo = KitchenRepository

    private var _beverageTypes by mutableStateOf<List<BeverageType>>(listOf())
    val beverageTypes: List<BeverageType> get() = _beverageTypes



    private var _selectedCategory by mutableStateOf(Category.BEERS)
    val selectedCategory: String get() = _selectedCategory.category

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
                val kitchenId: Int = stateHandler.appMode.kId

                res = kitchenRepo.getBeverageTypes(kitchenId, selectedCategory)


            }
            handleErrorKitchen(res)
        }
    }

    fun navToNextPage(location: String, beverageType: BeverageType){
        service.setBeverageType(beverageType)
        navController.navigate(location)
    }


    private fun handleErrorKitchen(response: Response<MutableList<BeverageType>>) {
        println(response.code())
        when (response.code()) {

            200 -> {
                response.body()?.let { _beverageTypes = it }
            }
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Unknown error ${response.code()}: ${response.message()}")
        }
    }




}