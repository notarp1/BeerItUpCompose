package com.notarmaso.beeritupcompose.views.beeritup.payments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.UserPaymentObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel(val s: Service): ViewModel() {

    private var _owedFrom by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owedFrom: List<UserPaymentObject>? get() = _owedFrom

    private var _owesTo by mutableStateOf<List<UserPaymentObject>?>(listOf())
    val owesTo: List<UserPaymentObject>? get() = _owesTo

    private val userRepo = UserRepository


    fun loadLists() {
        viewModelScope.launch {

            val resOwed: Response<List<UserPaymentObject>>
            val resOwes: Response<List<UserPaymentObject>>

            withContext(Dispatchers.IO){

                resOwed = userRepo.userOwed(s.stateHandler.appMode.uId)
                _owedFrom = resOwed.body()
                resOwes = userRepo.userOwes(s.stateHandler.appMode.uId)
                _owesTo = resOwes.body()
            }



        }
    }

    fun makePayment(ownerId: Int) {
        viewModelScope.launch {
            val res: Response<String>

            withContext(Dispatchers.IO) {

                res = userRepo.makePayment(s.stateHandler.appMode.uId, ownerId)

            }
        }
    }


}