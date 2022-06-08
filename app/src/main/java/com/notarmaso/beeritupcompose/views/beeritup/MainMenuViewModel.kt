package com.notarmaso.beeritupcompose.views.beeritup

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.*

class MainMenuViewModel(val s: Service) : ViewModel() {


    fun logInState(): StateHandler.AppMode {
        return s.stateHandler.appMode
    }

    fun isLoggedInAsKitchen(): Boolean{
        if(logInState().isSignedInAsKitchen()) {
            s.observer.notifySubscribers(FuncToRun.GET_USERS)
        }

        return logInState() is StateHandler.AppMode.SignedInAsKitchen
    }

    fun navigate(pages: Pages){
        println("HAHA" + s.currentPage)
        when(s.currentPage){
            Pages.ADD_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGE_TYPES)
            Pages.SELECT_BEVERAGE_STAGE_1 -> s.observer.notifySubscribers(FuncToRun.GET_BEVERAGES_IN_STOCK)
            else -> println("Doing something else")
        }
        s.navigate(pages)
    }

    fun logout(){
        s.stateHandler.logOut()
        s.navigate(Pages.START_MENU)
    }
}