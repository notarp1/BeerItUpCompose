package com.notarmaso.beeritupcompose.views.beeritup.payments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Category
import com.notarmaso.beeritupcompose.FuncToRun
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.Observable
import com.notarmaso.beeritupcompose.models.UserPaymentObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel(val s: Service): ViewModel(), Observable {

    private var _owedFrom by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owedFrom: List<UserPaymentObject>? get() = _owedFrom

    private var _owesTo by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owesTo: List<UserPaymentObject>? get() = _owesTo

    private val userRepo = UserRepository

    private var _selectedCategory by mutableStateOf(Category.MONEY_YOU_OWE)
    val selectedCategory: String get() = _selectedCategory.category

    init {
        s.observer.register(this)
    }

    private fun loadLists() {
        viewModelScope.launch {
            val resOwed: Response<List<UserPaymentObject>>
            val resOwes: Response<List<UserPaymentObject>>

            withContext(Dispatchers.IO){
                when(_selectedCategory){
                    Category.MONEY_YOU_OWE -> {
                        resOwes = userRepo.userOwes(s.stateHandler.appMode.uId)
                        _owesTo = resOwes.body()
                    }
                    Category.MONEY_YOU_ARE_OWED -> {
                        println("GAGAAG")
                        resOwed = userRepo.userOwed(s.stateHandler.appMode.uId)
                        _owedFrom = resOwed.body()
                    }
                    else -> { println("Else statement is run") }
                }
            }

        }
    }

    fun setCategory(category: Category) {
        _selectedCategory = category
        loadLists()
    }

    /*TODO: Payments*/
    fun makePayment(ownerId: Int) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                userRepo.makePayment(s.stateHandler.appMode.uId, ownerId)
            }
        }
    }

    override fun update(funcToRun: FuncToRun) {
       if(funcToRun == FuncToRun.GET_PAYMENTS){
           loadLists()
       }
    }


}