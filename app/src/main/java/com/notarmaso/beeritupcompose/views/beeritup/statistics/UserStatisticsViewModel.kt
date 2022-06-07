package com.notarmaso.db_access_setup.views.beeritup.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.BeverageType
import com.notarmaso.db_access_setup.models.LeaderboardEntryObj
import com.notarmaso.db_access_setup.models.UserRecieve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import retrofit2.Response


class UserStatisticsViewModel(val service: Service, val stateHandler: StateHandler, val navController: NavHostController) : ViewModel() {
    private val kitchenRepo = KitchenRepository

    private var _currentMonth: Int by mutableStateOf(Clock.System.todayAt(TimeZone.currentSystemDefault()).monthNumber)
    private var _currentYear: Int by mutableStateOf(Clock.System.todayAt(TimeZone.currentSystemDefault()).year)


    val currentMonth: String get() = getSelectedMonth(_currentMonth)

    private var _userList by mutableStateOf<List<LeaderboardEntryObj>?>(listOf())
    val userList: List<LeaderboardEntryObj>? get() = _userList


    fun incrementMonth(){
        if(_currentMonth >= 12) _currentMonth = 12
        else _currentMonth++

        reloadHighScores()

    }
    fun decrementMonth(){
        if(_currentMonth <= 0) _currentMonth = 0
        else _currentMonth --
        reloadHighScores()
    }

     fun reloadHighScores(){

        viewModelScope.launch() {

            val res = when(_currentMonth){
                0 -> {
                    withContext(Dispatchers.IO) {
                        kitchenRepo.getYearlyLeaderboard(stateHandler.appMode.kId, _currentYear)
                    }
                }
                else -> {
                    withContext(Dispatchers.IO) {
                        kitchenRepo.getMonthlyLeaderboard(stateHandler.appMode.kId, _currentYear, _currentMonth)
                    }
                }
            }

            handleResponseCode(res)



        }
    }

    private fun getSelectedMonth(number: Int): String {
        return when(number){
            0 -> "total"
            1 -> "january"
            2 -> "february"
            3 -> "march"
            4 -> "april"
            5 -> "may"
            6 -> "june"
            7 -> "july"
            8 -> "august"
            9 -> "september"
            10 -> "october"
            11 -> "november"
            12 -> "december"
            else -> "Error"
        }
    }

    private fun handleResponseCode(response: Response<List<LeaderboardEntryObj>>) {
        println(response.code())
        when (response.code()) {

            200 -> {
              _userList = response.body()
            }
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Unknown error ${response.code()}: ${response.message()}")
        }
    }


}