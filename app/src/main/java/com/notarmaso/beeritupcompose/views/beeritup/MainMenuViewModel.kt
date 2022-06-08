package com.notarmaso.beeritupcompose.views.beeritup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.interfaces.Observerable

class MainMenuViewModel(val s: Service) : ViewModel(), Observerable {


    private var _loggedInAsKitchen: Boolean = false
    val loggedInAsKitchen: Boolean get() = _loggedInAsKitchen

    fun logInState(): StateHandler.AppMode {
        return s.stateHandler.appMode
    }

    init {
        s.observer.register(this)
    }


    fun isLoggedInAsKitchen(){
        println("HAHA:" + logInState().isSignedInAsKitchen())
        if(logInState().isSignedInAsKitchen()) {
            s.observer.notifySubscribers(FuncToRun.GET_USERS)
        }

        _loggedInAsKitchen = logInState() is StateHandler.AppMode.SignedInAsKitchen
    }

    fun navigate(pages: Pages){
        when(s.currentPage){
            Pages.ADD_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGE_TYPES)
            Pages.SELECT_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGES_IN_STOCK)
            Pages.PAYMENTS -> {
                //If Signed in as kitchen, run in SelectUserViewModel
                if(s.stateHandler.appMode.isSignedInAsUser()) {
                    s.observer.notifySubscribers(FuncToRun.GET_PAYMENTS)
                }
            }
            else -> println("Doing something else")
        }
        s.navigate(pages)
    }



    fun logout(){
        s.stateHandler.logOut()
        s.navigate(Pages.START_MENU)
    }

    override fun update(methodToRun: FuncToRun) {
        if(methodToRun == FuncToRun.GET_LOGIN_STATE_2){
            isLoggedInAsKitchen()
            s.observer.notifySubscribers(FuncToRun.FINISHED_LOADING)
        }
    }
}