package com.notarmaso.beeritupcompose.views.mainMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromJsonToListInt
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserEntry
import com.notarmaso.beeritupcompose.models.UserLeaderboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


class MainMenuViewModel(val service: Service): ViewModel(), ViewModelFunction{

    private var _users by mutableStateOf<List<User>>(listOf())
    private var _currentMonthNumber:Int = 0


    private var _currentMonth by mutableStateOf("Month")
    val currentMonth: String get() = _currentMonth

    private var _userList = mutableStateListOf<UserLeaderboard>()
    val userList: List<UserLeaderboard> get() = _userList



    private val userRepository: UserRepository = UserRepository(service.context)

    init {
        service.observer.register(this)

       /*This makes the debugger crash*/
        Timer("Init", false).schedule(3500) {
            _currentMonth = service.currentDate
            reloadHighscores(true)
        }

    }

    fun setPage(page: String){
        service.currentPage = page

    }

    fun incrementMonth(){
        if(_currentMonthNumber >= 12) _currentMonthNumber = 12
        else _currentMonthNumber++
        reloadHighscores()

    }
    fun decrementMonth(){
        if(_currentMonthNumber <= 0) _currentMonthNumber = 0
        else _currentMonthNumber --
        reloadHighscores()
    }

    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update(page: String) {
        if(page == MainActivity.MAIN_MENU) {
            _currentMonth = service.currentDate
            reloadHighscores(true)
        }
    }





    private fun reloadHighscores(isIni: Boolean = false){
        if(!isIni)getSelectedMonth()
        viewModelScope.launch(Dispatchers.IO) {
            _userList.clear()

            val temp = userRepository.getAllUsers()

            for(user in temp){

                val totalBeersTemp = user.totalBeers.fromJsonToListInt()[_currentMonth]

                if(totalBeersTemp != null) {
                    _userList.add(UserLeaderboard(user.name, totalBeersTemp))
                }
            }
            _userList.sortByDescending { x -> x.count }


        }
    }

    private fun getSelectedMonth() {

        _currentMonth = when(_currentMonthNumber){
            0 -> "TOTAL"
            1 -> "JANUARY"
            2 -> "FEBRUARY"
            3 -> "MARCH"
            4 -> "APRIL"
            5 -> "MAY"
            6 -> "JUNE"
            7 -> "JULY"
            8 -> "AUGUST"
            9 -> "SEPTEMBER"
            10 -> "OCTOBER"
            11 -> "NOVEMBER"
            12 -> "DECEMBER"
            else -> "Error"
        }
    }


}