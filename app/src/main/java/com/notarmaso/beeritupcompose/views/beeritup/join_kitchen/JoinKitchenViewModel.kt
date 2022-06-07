package com.notarmaso.beeritupcompose.views.beeritup.join_kitchen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.StateHandler
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.interfaces.Form
import com.notarmaso.db_access_setup.models.Kitchen
import com.notarmaso.db_access_setup.models.KitchenLoginObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class JoinKitchenViewModel(
    private val s: Service
) : ViewModel(), Form {

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
            val currentUser = s.stateHandler.appMode as StateHandler.AppMode.SignedInAsUser

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
            400 -> s.makeToast("Error: This kitchen does not exist")
            401 -> s.makeToast("Error: Wrong password")
            500 -> s.makeToast(response.message())
            else -> s.makeToast(response.message())
        }
    }

    private fun handleErrorJoined(response: Response<String>, kId: Int) {
        when(response.code()){
            201 -> {
                s.makeToast("Successfully Joined!")
                s.stateHandler.onUserJoinedKitchen(kId)
                s.nav?.popBackStack()
            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast(response.message())
        }
    }



}