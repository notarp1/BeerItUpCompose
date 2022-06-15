package com.notarmaso.beeritup.views.start_screen.login_kitchen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.interfaces.Form
import kotlinx.coroutines.launch

class LoginKitchenViewModel(val s: Service) : ViewModel(), Form {



    private var _name by mutableStateOf("ST-Lige")
    val name: String get() = _name

    private var _password by mutableStateOf("112233")
    val password: String get() = _password

    val isLoading: Boolean get() = s.isLoading

    override fun setPass(newText: String, isPasswordConfirm: Boolean) {
        _password = newText
    }

    override fun setName(newText: String, isEmail: Boolean) {
        _name = newText
    }

    private fun resetTextFields(){
        _name = ""
        _password = ""
    }

    fun logInKitchen() {
        s.setLoading(true)
        viewModelScope.launch {
            s.logInKitchen(name, password)
        }
        resetTextFields()
    }





    override fun setPin(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }




}