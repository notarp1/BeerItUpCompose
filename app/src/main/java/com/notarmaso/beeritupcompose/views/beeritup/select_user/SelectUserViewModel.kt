package com.notarmaso.db_access_setup.views.beeritup.select_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.UserRecieve
import com.notarmaso.db_access_setup.dal.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SelectUserViewModel(val service: Service, val navController: NavHostController, val stateHandler: StateHandler) : ViewModel() {
    private val kitchenRepo = KitchenRepository

    private var _users by mutableStateOf<List<UserRecieve>?>(listOf())
    val userList: List<UserRecieve>? get() = _users

    fun getUsers(){

        viewModelScope.launch() {
            val res: Response<List<UserRecieve>>
            withContext(Dispatchers.IO) {
                res = kitchenRepo.getKitchenUsers(stateHandler.appMode.kId)
                _users = res.body()

            }
        }
    }

    fun navigateToPage(user: UserRecieve){
        val appMode = stateHandler.appMode as StateHandler.AppMode.SignedInAsKitchen
        stateHandler.setAppMode(StateHandler.AppMode.SignedInAsKitchen(appMode.kId, appMode.kPass, appMode.kName, user.id, user.name))
        navController.navigate(service.currentPage)

    }




}