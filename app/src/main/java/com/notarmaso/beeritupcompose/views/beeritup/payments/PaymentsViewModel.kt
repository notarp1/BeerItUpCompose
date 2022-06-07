package com.notarmaso.db_access_setup.views.beeritup.payments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.UserRepository
import com.notarmaso.db_access_setup.models.UserPaymentObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel(val navController: NavHostController, val service: Service, val stateHandler: StateHandler): ViewModel()  {

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

                resOwed = userRepo.userOwed(stateHandler.appMode.uId)
                _owedFrom = resOwed.body()
                resOwes = userRepo.userOwes(stateHandler.appMode.uId)
                _owesTo = resOwes.body()
            }



        }
    }

    fun makePayment(ownerId: Int) {
        viewModelScope.launch {
            val res: Response<String>

            withContext(Dispatchers.IO) {

                res = userRepo.makePayment(stateHandler.appMode.uId, ownerId)

            }
        }
    }
}