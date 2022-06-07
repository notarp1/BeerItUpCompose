package com.notarmaso.db_access_setup.views.beeritup.join_kitchen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.Service
import com.notarmaso.db_access_setup.dal.repositories.KitchenRepository
import com.notarmaso.db_access_setup.models.Kitchen
import com.notarmaso.db_access_setup.models.KitchenLoginObject
import com.notarmaso.db_access_setup.views.beeritup.interfaces.Form
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class JoinKitchenViewModel(
    private val service: Service,
    private val navController: NavHostController,
    private val stateHandler: StateHandler) : ViewModel(), Form {

    private val kitchenRepo = KitchenRepository


    private var _kName by mutableStateOf("")
    val kName: String get() = _kName

    private var _kPassword by mutableStateOf("")
    val kPassword: String get() = _kPassword


    override fun setName(newText: String) {
        _kName = newText
    }

    override fun setPass(newText: String) {
        _kPassword = newText
    }

    override fun setPin(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }


    fun authorizeKitchen(){
       viewModelScope.launch {
           val res: Response<Kitchen>
           val kitchenLoginObject = KitchenLoginObject(kName, kPassword)
           withContext(Dispatchers.IO){
               res = kitchenRepo.login(kitchenLoginObject)
           }
           handleErrorKitchen(res)


       }
    }

    /* Skal laves mere rigtig, nok uden sharedPrefs*/
    private fun handleKitchenRegistration(res: Response<Kitchen>){
        viewModelScope.launch {
            val kitchen: Kitchen? = res.body()
            val response: Response<String>
            val currentUser = stateHandler.appMode as StateHandler.AppMode.SignedInAsUser

            if(kitchen != null) {
                withContext(Dispatchers.IO) {
                    response = kitchenRepo.addKitchenUser(kitchen.id, currentUser.uId)
                }
                handleErrorJoined(response, kitchen.id)
            }


        }
    }
    private fun handleErrorKitchen(response: Response<Kitchen>) {
        when(response.code()){
            200 -> {
                handleKitchenRegistration(response)
            }
            400 -> service.makeToast("Error: This kitchen does not exist")
            401 -> service.makeToast("Error: Wrong password")
            500 -> service.makeToast(response.message())
            else -> service.makeToast(response.message())
        }
    }

    private fun handleErrorJoined(response: Response<String>, kId: Int) {
        when(response.code()){
            201 -> {
                service.makeToast("Successfully Joined!")
                stateHandler.onUserJoinedKitchen(kId)
                navController.popBackStack()
            }
            500 -> service.makeToast(response.message())
            else -> service.makeToast(response.message())
        }
    }



}