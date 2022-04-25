package com.notarmaso.beeritupcompose.views.mainMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromJsonToListInt
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserLeaderboard
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone


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
    }

  /*  suspend fun getHighScores(onComplete: () -> Unit) {
        _currentMonth = Clock.System.todayAt(TimeZone.currentSystemDefault()).month.toString()
        reloadHighScores(true)
    }*/

    fun setPage(page: Pages){
        service.setCurrentPage(page)
    }

    fun incrementMonth(){
        if(_currentMonthNumber >= 12) _currentMonthNumber = 12
        else _currentMonthNumber++
        reloadHighScores()

    }
    fun decrementMonth(){
        if(_currentMonthNumber <= 0) _currentMonthNumber = 0
        else _currentMonthNumber --
        reloadHighScores()
    }

    override fun navigate(location: Pages){
        service.navigate(location)
    }
    override fun navigateBack(location: Pages){
        service.navigateBack(location)
    }

    override fun update(page: String) {
        if(page == Pages.MAIN_MENU.value) {
            _currentMonth = service.currentDate
            reloadHighScores(true)
        } else if(page == "init"){
            _currentMonth = Clock.System.todayAt(TimeZone.currentSystemDefault()).month.toString()
            reloadHighScores(true)
        }
    }





    private fun reloadHighScores(isIni: Boolean = false){
        if(!isIni)getSelectedMonth()
        viewModelScope.launch() {
            _userList.clear()

            val temp = userRepository.getAllUsersReplace()

            for (user in temp) {
                val totalBeersTemp = user.totalBeers.fromJsonToListInt()[_currentMonth]

                if (totalBeersTemp != null) {
                    _userList.add(UserLeaderboard(user.name, totalBeersTemp))
                }
            }


            _userList.sortByDescending { x -> x.count }

            service.observer.notifySubscribers("finishedLoading")

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