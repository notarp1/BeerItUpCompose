package com.notarmaso.beeritupcompose.views.beeritup

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.StateHandler

class MainMenuViewModel(val s: Service) : ViewModel() {


    fun logInState(): StateHandler.AppMode {
        return s.stateHandler.appMode
    }

    fun isLoggedInAsUser(): Boolean{
        return logInState() is StateHandler.AppMode.SignedInAsUser
    }

    fun logout(){
        s.stateHandler.logOut()

        s.navigate(Pages.START_MENU)
    }
}