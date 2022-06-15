package com.notarmaso.beeritup.views.start_screen.login_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.interfaces.Form
import kotlinx.coroutines.launch


class LoginUserViewModel(val s: Service) : ViewModel(), Form {


    private var _phone by mutableStateOf("22270704")
    val phone: String get() = _phone

    private var _password by mutableStateOf("112233")
    val password: String get() = _password

    private var _email by mutableStateOf("slippen4@gmail.com")
    private val email: String get() = _email


    override fun setName(newText: String, isEmail: Boolean) {
        if(isEmail) _email = newText
    }

    override fun setPass(newText: String, isPasswordConfirm: Boolean) {
        _password = newText
    }

    override fun setPin(newText: String) {
        TODO("Not yet implemented")

    }

    override fun setPhone(newText: String) {
        _phone = newText

    }



    private fun resetTextFields(){
        _email = ""
        _password = ""
    }

    fun logInUser(){
        viewModelScope.launch() {
            s.logInUser(email, password)
        }
        resetTextFields()
    }




}