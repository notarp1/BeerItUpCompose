package com.notarmaso.beeritupcompose.views.start_screen

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service


class StartMenuViewModel(val s: Service) : ViewModel() {


    fun getStatus() {

        if (s.stateHandler.wasLoggedInUser()) {
            s.stateHandler.onUserWasLoggedIn()
            s.navigateAndPopUpTo(Pages.MAIN_MENU)
        } else if (s.stateHandler.wasLoggedInKitchen()) {
            s.stateHandler.onKitchenWasLoggedIn()
            s.navigateAndPopUpTo(Pages.MAIN_MENU)
        }

    }
}


