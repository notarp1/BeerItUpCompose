package com.notarmaso.beeritupcompose.views.start_screen.login_kitchen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.interfaces.Form
import com.notarmaso.db_access_setup.models.Kitchen
import com.notarmaso.db_access_setup.models.KitchenLoginObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginKitchenViewModel(val s: Service) : ViewModel(), Form {

    val kitchenRepo = KitchenRepository

    private var _name by mutableStateOf("st-lige")
    val name: String get() = _name

    private var _password by mutableStateOf("112233")
    val password: String get() = _password


    override fun setPass(newText: String) {
        _password = newText
    }

    override fun setName(newText: String) {
        _name = newText
    }

    private fun resetTextFields(){
        _name = ""
        _password = ""
    }

    fun logInKitchen() {
        s.logInKitchen(name, password)
        resetTextFields()
    }





    override fun setPin(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }


}