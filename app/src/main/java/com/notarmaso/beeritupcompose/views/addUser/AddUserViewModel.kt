package com.notarmaso.beeritupcompose.views.addUser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class AddUserViewModel(val service: Service): ViewModel() {

    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var owedFrom by mutableStateOf<Map<String, Double>?>(null)
    var owesTo by mutableStateOf<Map<String, Double>?>(null)

    var errorFound: Boolean = false

    fun navigateBack(location: String){
        service.navigateBack(location)
    }

    fun onSubmit(){
        filterWhitespaces()
        if(phoneValidation()){
           // val users = service.db.userDao().getAll()



            val user = User(name, phone, null, null)

            viewModelScope.launch(Dispatchers.IO){
                try {
                    service.db.userDao().insertUser(user)
                    service.userObs.notifySubscribers()

                } catch (e: Exception){
                    Timber.d("Username already exists %s", e.toString())
                    errorFound = true
                }
            }

            if(errorFound) service.makeToast("Username is already taken!")
            else service.navigateBack(MainActivity.MAIN_MENU)

        }





    }

    private fun filterWhitespaces(){
        name.filter { !it.isWhitespace() }
        phone.filter { !it.isWhitespace() }
    }
    private fun phoneValidation():Boolean {
        if (phone.length == 8) {
            return try {
                phone.toInt()
                true
            } catch (e: Exception) {
                service.makeToast("Number should only contain digits!")
                false
            }
        }
        service.makeToast("Number must be 8 digits!")
        return false
    }


}


