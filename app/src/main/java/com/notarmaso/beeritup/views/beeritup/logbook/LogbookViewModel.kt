package com.notarmaso.beeritup.views.beeritup.logbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.interfaces.Observable
import com.notarmaso.beeritup.models.BeverageLogEntryObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LogbookViewModel(val s: Service, private val userRepo: UserRepository) : ViewModel(), Observable {


    private var _logs by mutableStateOf<List<BeverageLogEntryObj>?>(listOf())
    val logs: List<BeverageLogEntryObj>? get() = _logs?.reversed()

    private var _selectedOption: Int by mutableStateOf(0)
    val selectedOptionString: String get() = getSelectedCategory(_selectedOption)
    val selectedOption: Int get() = _selectedOption


    val isLoading: Boolean get() = s.isLoading


    init {
        s.observer.register(this)
    }

    fun incrementOption() {
        if (_selectedOption >= 3) _selectedOption = 3
        else {
            _selectedOption++
            getLogs(null)
        }
    }

    fun decrementOption() {
        if (_selectedOption <= 0) _selectedOption = 0
        else {
            _selectedOption--
            getLogs(null)
        }

    }

    private fun getLogs(selectedOption: Int?) {

        if (selectedOption != null) {
            _selectedOption = selectedOption
        }
        viewModelScope.launch(Dispatchers.Main) {
            var res: Response<List<BeverageLogEntryObj>>
            val uId = s.stateHandler.appMode.uId

            withContext(Dispatchers.IO) {
                s.setLoading(true)
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

    enum class LogCategory(val value: String){
        SOLD("sold"),
        PAID("paid"),
        ADDED("added"),
        CONSUMED("consumed")
    }

    private fun getSelectedCategory(number: Int): String {
        return when (number) {
            0 -> LogCategory.SOLD.value
            1 -> LogCategory.PAID.value
            2 -> LogCategory.ADDED.value
            3 -> LogCategory.CONSUMED.value

            else -> "Error"
        }
    }

    private fun handleResponseCode(response: Response<List<BeverageLogEntryObj>>) {
        when (response.code()) {
            200 ->  _logs = response.body()
            else -> s.makeToast("Error: ${response.message()}")
        }
        s.setLoading(false)
    }

    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_LOGS){
            getLogs(0)
        }
    }


}