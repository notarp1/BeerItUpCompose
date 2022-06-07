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

    private val userRepo = UserRepository


    private var _phone by mutableStateOf("22270704")
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

    fun logInUser(){
       viewModelScope.launch {
           val res: Response<UserRecieve>
           val userLoginObj = UserLoginObject(phone, password)
           withContext(Dispatchers.IO){
               res = userRepo.login(userLoginObj)
           }
           handleErrorUser(res)

       }
    }


    private fun handleUser(res: Response<UserRecieve>){
        viewModelScope.launch {
            val user: UserRecieve? = res.body()

            withContext(Dispatchers.IO){
                if (user != null) {
                   val userDetails = s.stateHandler.getAssignedDetails(user.id)

                    if (userDetails != null) {


                        s.stateHandler.onUserSignInSuccess(user, userDetails)
                    }
                }
            }
            s.navigatePopUpToInclusive(Pages.MAIN_MENU, Pages.LOGIN_KITCHEN)
            _phone = ""
            _password = ""


        }
    }



    private fun handleErrorUser(response: Response<UserRecieve>) {
        when(response.code()){
            200 -> {
                s.makeToast("Login Succesful!")
                handleUser(response)
            }
            400 -> s.makeToast("Error: This User Does Not Exist")
            401 -> s.makeToast("Error: Wrong password")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown")
        }
    }



}