package com.notarmaso.beeritup.views.beeritup.select_user

import android.graphics.pdf.PdfDocument.Page
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.StateHandler
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.interfaces.Observable
import com.notarmaso.beeritup.models.UserRecieve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectUserViewModel(val s: Service, private val kitchenRepo: KitchenRepository) : ViewModel(), Observable {


    private var _users by mutableStateOf<List<UserRecieve>?>(listOf())
    val userList: List<UserRecieve>? get() = _users

    init {
        s.observer.register(this)
    }

    private fun getUsers(){

        viewModelScope.launch() {
            val res: Response<List<UserRecieve>>
            withContext(Dispatchers.IO) {
                res = kitchenRepo.getKitchenUsers(s.stateHandler.appMode.kId)
                _users = res.body()

            }
        }
    }

    fun navigateToPage(user: UserRecieve){
        val appMode = s.stateHandler.appMode as StateHandler.AppMode.SignedInAsKitchen
        s.stateHandler.setAppMode(StateHandler.AppMode.SignedInAsKitchen(appMode.kId, appMode.kPass, appMode.kName, user.id, user.name))

        when(s.currentPage){
            Pages.PAYMENTS -> s.observer.notifySubscribers(FuncToRun.GET_PAYMENTS)
            Pages.LOGBOOKS -> s.observer.notifySubscribers(FuncToRun.GET_LOGS)
            else -> println("else statement is run")
        }

        s.navigate(s.currentPage)

    }



    override fun update(funcToRun: FuncToRun) {
        if (funcToRun == FuncToRun.GET_USERS) {
            getUsers()
        }
    }


}