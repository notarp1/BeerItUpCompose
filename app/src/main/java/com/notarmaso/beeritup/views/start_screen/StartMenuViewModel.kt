package com.notarmaso.beeritup.views.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.interfaces.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class StartMenuViewModel(val s: Service) : ViewModel(), Observable {

    init {
        s.observer.register(this)
    }


    private fun getStatus() {

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


