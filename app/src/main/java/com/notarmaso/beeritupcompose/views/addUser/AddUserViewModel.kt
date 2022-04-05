package com.notarmaso.beeritupcompose.views.addUser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromListFloatToJson
import com.notarmaso.beeritupcompose.fromListIntToJson
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class AddUserViewModel(val service: Service): ViewModel() {

    private val userRepository: UserRepository = UserRepository(service.context)
    var name by mutableStateOf("")
    var phone by mutableStateOf("")


    private var errorFound: Boolean = false

    fun navigateBack(location: String){
        service.navigateBack(location)
    }

    fun onSubmit(){
        filterWhitespaces()
        if(phoneValidation()){
           // val users = service.db.userDao().getAll()
            val owedFrom: MutableMap<String, Float> = mutableStateMapOf()
            val owesTo: MutableMap<String, Float> = mutableStateMapOf()
            val totalBeers: MutableMap<String, Int> = mutableStateMapOf(
                "TOTAL" to 0,
                "JANUARY" to 0,
                "FEBRUARY" to 0,
                "MARCH" to 0,
                "APRIL" to 0,
                "MAY" to 0,
                "JUNE" to 0,
                "JULY" to 0,
                "AUGUST" to 0,
                "SEPTEMBER" to 0,
                "OCTOBER" to 0,
                "NOVEMBER" to 0,
                "DECEMBER" to 0
            )


            val user = User(name, phone, owedFrom.fromListFloatToJson(), owesTo.fromListFloatToJson(), totalBeers = totalBeers.fromListIntToJson())
            service.createAlertBoxAddUser(user){submitUser(user)}

        }
    }

    private fun submitUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.insertUser(user = user)
            } catch (e: Exception) {
                Timber.d("Username already exists %s", e.toString())
                errorFound = true
            } finally {

                viewModelScope.launch(Dispatchers.Main) {
                    if (!errorFound) {
                        service.userObs.notifySubscribers()
                        service.navigateBack(MainActivity.MAIN_MENU)
                    } else {
                        service.makeToast("Username is already taken!")
                        errorFound = false
                    }

                }
            }
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


