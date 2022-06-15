package com.notarmaso.beeritup.views.beeritup.payments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Category
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.interfaces.Observable
import com.notarmaso.beeritup.models.UserPaymentObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel(val s: Service, private val userRepo: UserRepository): ViewModel(), Observable {

    private var _owedFrom by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owedFrom: List<UserPaymentObject>? get() = _owedFrom

    private var _owesTo by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owesTo: List<UserPaymentObject>? get() = _owesTo



    private var _selectedCategory by mutableStateOf(Category.MONEY_YOU_OWE)
    val selectedCategory: String get() = _selectedCategory.category

    init {
        s.observer.register(this)
    }

    private suspend fun loadLists() {

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

    fun setCategory(category: Category) {
        _selectedCategory = category
        viewModelScope.launch(){
            loadLists()
        }

    }

    fun onAccept(ownerId: Int){
        viewModelScope.launch(){
            makePayment(ownerId)
        }
    }

    /*TODO: Payments*/
    private suspend fun makePayment(ownerId: Int) {

        withContext(Dispatchers.IO) {
            userRepo.makePayment(s.stateHandler.appMode.uId, ownerId)
            loadLists()
        }

    }

    override fun update(funcToRun: FuncToRun) {
       if(funcToRun == FuncToRun.GET_PAYMENTS){
           setCategory(Category.MONEY_YOU_OWE)
       }
    }


}