package com.notarmaso.beeritupcompose.views.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.FuncToRun
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.interfaces.Observerable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartMenuViewModel(val s: Service) : ViewModel(), Observerable {

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
        }
    }



    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.GET_LOGIN_STATE ){
            getStatus()


        }
    }
}


