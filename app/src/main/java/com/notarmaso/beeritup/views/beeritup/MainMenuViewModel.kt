package com.notarmaso.beeritup.views.beeritup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.notarmaso.beeritup.*
import com.notarmaso.beeritup.interfaces.Observable

class MainMenuViewModel(val s: Service) : ViewModel(), Observable {



    private var _loggedInAsKitchen: Boolean by mutableStateOf(false)
    val loggedInAsKitchen: Boolean get() = _loggedInAsKitchen

    fun logInState(): StateHandler.AppMode {
        return s.stateHandler.appMode
    }

    init {
        s.observer.register(this)
    }


    private fun loginStateSetup(){
        if(logInState().isSignedInAsKitchen()) {
            s.observer.notifySubscribers(FuncToRun.GET_USERS)
        }

        _loggedInAsKitchen = logInState() is StateHandler.AppMode.SignedInAsKitchen

    }



    fun navigate(pages: Pages){
        //If Signed in as kitchen, run in SelectUserViewModel
        val isAnUser = s.stateHandler.appMode.isSignedInAsUser()

        when(s.currentPage){
            Pages.ADD_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGE_TYPES)
            Pages.SELECT_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGES_IN_STOCK)
            Pages.LOGBOOKS -> {
                if(isAnUser) s.observer.notifySubscribers(FuncToRun.GET_LOGS)
            }
            Pages.PAYMENTS -> {
                if(isAnUser) s.observer.notifySubscribers(FuncToRun.GET_PAYMENTS)
            }
            else -> println("Doing something else")
        }
        s.navigate(pages)
    }



    fun logout(){
        s.stateHandler.logOut()
        s.navigate(Pages.START_MENU)
    }

    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_LOGIN_STATE_2){
            loginStateSetup()
            s.observer.notifySubscribers(FuncToRun.FINISHED_LOADING)
        }
    }
}