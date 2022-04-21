package com.notarmaso.beeritupcompose.views.logBooks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogBookViewModel(val service: Service): ViewModel(), ViewModelFunction {

    private var _currentPageNumber:Int = 0

    private val userRepository: UserRepository = UserRepository(service.context)

    private var _currentPage by mutableStateOf("Payments")
    val currentPage: String get() = _currentPage

    private var _totalBought: String by mutableStateOf("0")
    val totalBought: String get() = _totalBought

    private var _totalAdded: String by mutableStateOf("0")
    val totalAdded: String get() = _totalAdded

    private var _logList = mutableStateListOf<String>()
    val loglist: List<String> get() = _logList

    init {
        service.observer.register(this)
    }

    fun incrementMonth(){
        if(_currentPageNumber >= 2) _currentPageNumber = 2
        else _currentPageNumber++
        reloadLogs()

    }
    fun decrementMonth(){
        if(_currentPageNumber <= 0) _currentPageNumber = 0
        else _currentPageNumber --
        reloadLogs()
    }

    private fun reloadLogs(){
        getSelectedMonth()
        val name = service.currentUser.name

        viewModelScope.launch() {

            withContext(Dispatchers.IO){
                _logList.clear()
                _totalAdded = userRepository.getTotalAdded(name).roundOff()
                _totalBought = userRepository.getTotalBought(name).roundOff()

                when(_currentPageNumber){
                    0 -> {
                        val list = userRepository.getTranscationsLog(name).fromJsonToListReversed()
                        for(x in list){
                            _logList.add(x)
                         }
                    }
                    1 ->  {
                        val list = userRepository.getBoughtLog(name).fromJsonToListReversed()
                        var curListItem = 0
                        for(x in list){
                            curListItem++
                            if(curListItem >= 50) return@withContext
                            _logList.add(x)
                        }
                    }
                    2 ->  {
                        val list = userRepository.getAddedLog(name).fromJsonToListReversed()
                        for(x in list){
                            _logList.add(x)
                        }
                    }
                    else -> println("Error")
                }
            }
        }
    }

    private fun getSelectedMonth() {

        _currentPage = when(_currentPageNumber){
            0 -> "Transactions"
            1 -> "Beers Bought"
            2 -> "Beers Added"
            else -> "Error"
        }
    }

    override fun navigate(location: Pages) {

    }

    override fun navigateBack(location: Pages) {
        service.navigate(location = location)
    }

    override fun update(page: String) {
        if(page == Pages.LOG_BOOKS.value){
        reloadLogs()
        }

    }
}