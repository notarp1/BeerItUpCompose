package com.notarmaso.beeritupcompose.views.start_screen.login_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.Form
import com.notarmaso.beeritupcompose.models.UserLoginObject
import com.notarmaso.beeritupcompose.models.UserRecieve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginUserViewModel(val s: Service) : ViewModel(), Form {


    private var _phone by mutableStateOf("22270708")
    val phone: String get() = _phone

    private var _password by mutableStateOf("112233")
    val password: String get() = _password


    override fun setName(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPass(newText: String) {
        _password = newText
    }

    override fun setPin(newText: String) {
        TODO("Not yet implemented")

    }

    override fun setPhone(newText: String) {
        _phone = newText

    }

    private fun resetTextFields(){
        _phone = ""
        _password = ""
    }

    fun logInUser(){
        s.logInUser(phone, password)
        resetTextFields()
    }




}