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


    fun logInKitchen() {
        viewModelScope.launch {
            val res: Response<Kitchen>
            val kitchenLoginObj = KitchenLoginObject(name, password)
            withContext(Dispatchers.IO) {
                res = kitchenRepo.login(kitchenLoginObj)
            }
            handleErrorKitchen(res)

        }
    }


    private fun handleKitchen(res: Response<Kitchen>) {
        viewModelScope.launch {
            val kitchen: Kitchen? = res.body()

            withContext(Dispatchers.IO) {
                if (kitchen != null) {
                    s.stateHandler.onKitchenSignInSuccess(kitchen)
                }
            }
            s.navigatePopUpToInclusive(Pages.MAIN_MENU, Pages.LOGIN_KITCHEN)
            _name = ""
            _password = ""

        }
    }

    private fun handleErrorKitchen(response: Response<Kitchen>) {
        when (response.code()) {
            200 -> {
                s.makeToast("Login Succesful!")
                handleKitchen(response)
            }
            400 -> s.makeToast("Error: This Kitchen Does Not Exist")
            401 -> s.makeToast("Error: Wrong password")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown: ${response.message()}")
        }
    }


    override fun setPin(newText: String) {
        TODO("Not yet implemented")
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }


}