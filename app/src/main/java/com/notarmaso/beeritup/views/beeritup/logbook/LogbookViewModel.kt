package com.notarmaso.db_access_setup.views.beeritup.logbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.models.BeverageLogEntryObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LogbookViewModel(
    val s: Service,
    private val userRepo: UserRepository
) : ViewModel() {


    private var _logs by mutableStateOf<List<BeverageLogEntryObj>?>(listOf())
    val logs: List<BeverageLogEntryObj>? get() = _logs?.reversed()

    private var _selectedOption: Int by mutableStateOf(0)
    val selectedOption: String get() = getSelectedMonth(_selectedOption)
    val selectedOptionInt: Int get() = _selectedOption


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
            val uId = s.stateHandler.appMode.uId


            withContext(Dispatchers.IO) {
                when (_selectedOption) {

                    0 -> {
                        res = userRepo.soldLogbook(uId)
                        handleResponseCode(res)

                    }
                    1 -> {
                        res = userRepo.paidLogbook(uId)
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
            0 -> "sold"
            1 -> "paid"
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
                500 -> s.makeToast(response.message())
                else -> s.makeToast("Unknown error ${response.code()}: ${response.message()}")
            }
        }
    }


}