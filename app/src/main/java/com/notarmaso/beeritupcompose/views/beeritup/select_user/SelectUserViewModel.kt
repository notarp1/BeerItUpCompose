package com.notarmaso.beeritupcompose.views.beeritup.select_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.FuncToRun
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.StateHandler
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.interfaces.Observerable
import com.notarmaso.beeritupcompose.models.UserRecieve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectUserViewModel(val s: Service) : ViewModel(), Observerable {
    private val kitchenRepo = KitchenRepository

    private var _users by mutableStateOf<List<UserRecieve>?>(listOf())
    val userList: List<UserRecieve>? get() = _users

    init {
        s.observer.register(this)
    }

    fun getUsers(){

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
            else -> println("else statement is run")
        }

        s.navigate(s.currentPage)

    }



    override fun update(methodToRun: FuncToRun) {
        if(methodToRun == FuncToRun.GET_USERS){
            getUsers()
        }

    }


}