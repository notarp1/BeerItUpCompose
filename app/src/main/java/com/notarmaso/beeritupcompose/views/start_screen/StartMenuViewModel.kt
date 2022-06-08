package com.notarmaso.beeritupcompose.views.start_screen

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.FuncToRun
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.interfaces.Observable



class StartMenuViewModel(val s: Service) : ViewModel(), Observable {

    init {
        s.observer.register(this)
    }

    /*TODO REMOVE DELAY AND SCOPE THIS*/
    fun getStatus() {

            if (s.stateHandler.wasLoggedInUser()) {
                s.stateHandler.onUserWasLoggedIn()
                s.navigateAndClearBackstack(Pages.MAIN_MENU)

            } else if (s.stateHandler.wasLoggedInKitchen()) {
                s.stateHandler.onKitchenWasLoggedIn()
                s.navigateAndClearBackstack(Pages.MAIN_MENU)
            } else {
                s.observer.notifySubscribers(FuncToRun.FINISHED_LOADING)
            }


    }



    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_LOGIN_STATE ){
            getStatus()


        }
    }
}


