package com.notarmaso.beeritup.views.beeritup.join_kitchen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.StateHandler
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.interfaces.Form
import com.notarmaso.beeritup.models.Kitchen
import com.notarmaso.beeritup.models.KitchenLoginObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class JoinKitchenViewModel(
    val s: Service,
    private val kitchenRepo: KitchenRepository
) : ViewModel(), Form {




    private var _kName by mutableStateOf("")
    val kName: String get() = _kName

    private var _kPassword by mutableStateOf("")
    val kPassword: String get() = _kPassword


    override fun setName(newText: String, isEmail: Boolean) {
        _kName = newText
    }

    override fun setPass(newText: String, isPasswordConfirm: Boolean) {
        _kPassword = newText
    }

    override fun setPin(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }


    fun onAssignPressed(){
        viewModelScope.launch {
            authorizeKitchen()
        }
    }

    private  suspend fun authorizeKitchen(){

       val res: Response<Kitchen>
       val kitchenLoginObject = KitchenLoginObject(kName, kPassword)

       withContext(Dispatchers.IO){
           res = kitchenRepo.login(kitchenLoginObject)
       }

       if(res.isSuccessful){
           handleKitchenRegistration(res)
           return
       }

       s.makeToast("Error: " + res.message())


    }

    /* Skal laves mere rigtig, nok uden sharedPrefs*/
    private suspend fun handleKitchenRegistration(res: Response<Kitchen>){
        val kitchen: Kitchen? = res.body()
        val response: Response<String>
        val currentUser = s.stateHandler.appMode as StateHandler.AppMode.SignedInAsUser

        if(kitchen != null) {
            withContext(Dispatchers.IO) {
                response = kitchenRepo.addKitchenUser(kitchen.id, currentUser.uId)
            }
            if(response.isSuccessful){
                s.makeToast("Successfully Joined!")
                s.stateHandler.onUserJoinedKitchen(kitchen.id)
                s.nav?.popBackStack()
                return
            }
            s.makeToast("Error: " + response.message())
        }

    }






}