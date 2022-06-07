package com.notarmaso.db_access_setup.views.beeritup.logbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.dal.repositories.UserRepository
import com.notarmaso.db_access_setup.models.BeverageLogEntryObj
import com.notarmaso.db_access_setup.models.LeaderboardEntryObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.math.log

class LogbookViewModel(
    val service: Service,
    val stateHandler: StateHandler,
    val nav: NavHostController
) : ViewModel() {


    private var _logs by mutableStateOf<List<BeverageLogEntryObj>?>(listOf())
    val logs: List<BeverageLogEntryObj>? get() = _logs

    private var _selectedOption: Int by mutableStateOf(0)
    val selectedOption: String get() = getSelectedMonth(_selectedOption)
    val selectedOptionInt: Int get() = _selectedOption
    private val userRepo = UserRepository

    fun incrementOption() {
        if (_selectedOption >= 3) _selectedOption = 3
        else {
            _selectedOption++
            getLogs()
            println(logs)
        }




    }

    fun decrementOption() {
        if (_selectedOption <= 0) _selectedOption = 0
        else {
            _selectedOption--
            getLogs()
            println(logs)

        }

    }

    fun getLogs() {
        viewModelScope.launch(Dispatchers.Main) {
            var res: Response<List<BeverageLogEntryObj>>
            val uId = stateHandler.appMode.uId


            withContext(Dispatchers.IO) {
                when (_selectedOption) {
                    0 -> {
                        res = userRepo.boughtLogbook(uId)
                        handleResponseCode(res)
                    }
                    1 -> {
                        res = userRepo.soldLogbook(uId)
                        handleResponseCode(res)

                    }
                    2 -> {
                        res = userRepo.addedLogbook(uId)
                        handleResponseCode(res)
                        println(res.body())

                    }
                    3 -> {

                        res = userRepo.consumedLogbook(uId)
                        handleResponseCode(res)

                    }
                    else -> println("Error")
                }


            }
        }
    }

    private fun getSelectedMonth(number: Int): String {
        return when (number) {
            0 -> "bought"
            1 -> "sold"
            2 -> "added"
            3 -> "consumed"

            else -> "Error"
        }
    }

    private fun handleResponseCode(response: Response<List<BeverageLogEntryObj>>) {
        println(response.code())
        viewModelScope.launch(Dispatchers.Main) {
            when (response.code()) {

                200 -> {
                    _logs = response.body()
                }
                500 -> service.makeToast(response.message())
                else -> service.makeToast("Unknown error ${response.code()}: ${response.message()}")
            }
        }
    }


}