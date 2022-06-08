package com.notarmaso.beeritupcompose.views.start_screen.add_user

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.FuncToRun
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.StateHandler
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.Form
import com.notarmaso.beeritupcompose.models.UserRecieve
import com.notarmaso.beeritupcompose.models.UserToPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddUserViewModel(private val s: Service) : ViewModel(), Form {

    private val userRepo = UserRepository
    private val kitchenRepo = KitchenRepository

    private var _name by mutableStateOf("")
    val name: String get() = _name

    private var _phone by mutableStateOf("")
    val phone: String get() = _phone

    private var _password by mutableStateOf("")
    val password: String get() = _password

    private var _pin by mutableStateOf("")
    val pin: String get() = _pin


    override fun setName(newText: String) {
        _name = newText
    }

    override fun setPass(newText: String) {
        _password = newText
    }

    override fun setPin(newText: String) {
        _pin = newText
    }

    override fun setPhone(newText: String) {
        _phone = newText
    }


    /* VALIDATION */

    private fun filterWhitespaces() {
        name.filter { !it.isWhitespace() }
        phone.filter { !it.isWhitespace() }
    }

    private fun passValidation(): Boolean {
        if (password.length < 6) {
            Toast.makeText(s.context, "Password must be at least 6 digits!", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun nameValidation(): Boolean {
        if (name.length < 4) {
            Toast.makeText(s.context, "Name must be longer than 4 characters!", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun pinValidation(): Boolean {
        if (pin.length in 4..8) {
            return true
        }
        Toast.makeText(s.context, "Pin must be between 4 and 8 digits!", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun phoneValidation(): Boolean {
        if (phone.length == 8) {
            return try {
                phone.toInt()
                true
            } catch (e: Exception) {
                Toast.makeText(s.context, "Number should only contain digits!", Toast.LENGTH_SHORT)
                    .show()
                false
            }
        }

        Toast.makeText(s.context, "Number must be 8 digits!", Toast.LENGTH_SHORT).show()

        return false
    }

    private fun resetTextFields() {
        _name = ""
        _password = ""
        _pin = ""
        _phone = ""
    }

    /*USER CREATION*/

    fun createUser() {

        if (nameValidation() && passValidation() && pinValidation()) {
            filterWhitespaces()
            try {
                val pinToPost = pin.toInt()
                if (phoneValidation()) {
                    val user = UserToPost(
                        name,
                        phone,
                        password,
                        pinToPost
                    )
                    onSubmit(user)

                }

            } catch (e: Exception) {
                s.makeToast("Error: Only digits is allowed")
            }
        }
    }


    private fun onSubmit(user: UserToPost) {
        viewModelScope.launch() {
            val res: Response<UserRecieve>
            withContext(Dispatchers.IO) { res = userRepo.addUser(user) }
            handleErrorUser(res)


        }
        // service.createAlertBoxAddUser(user){submitUser(user)}
    }

    private fun handleErrorUser(response: Response<UserRecieve>) {
        when (response.code()) {
            201 -> {
                s.makeToast("User Created!")
                when(s.stateHandler.appMode){
                    is StateHandler.AppMode.SignedInAsKitchen -> {
                        response.body()?.let { addUserToKitchen(it.id) }
                    }
                    is StateHandler.AppMode.SignedOut -> logInUser()
                    else -> {
                        println("Else statement run")
                    }
                }
            }
            400 -> s.makeToast(response.message())
            409 -> s.makeToast("Error: A user with this number already exist")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown")
        }
    }

    private fun addUserToKitchen(id: Int) {
        val state = s.stateHandler.appMode as StateHandler.AppMode.SignedInAsKitchen
        handleKitchenRegistration(state, id)


    }
    /*LOGIN AUTOMATICALLY*/

    private fun logInUser() {
        s.logInUser(phone, password)
        resetTextFields()
    }

    /* JOIN KITCHEN IF USER IS CREATED FROM KITCHEN ACC*/
    private fun handleKitchenRegistration(state: StateHandler.AppMode.SignedInAsKitchen, id: Int){
        viewModelScope.launch {
            val response: Response<String>
            withContext(Dispatchers.IO) {
                response = kitchenRepo.addKitchenUser(state.kId, id)
            }
            handleErrorJoined(response)
        }
    }


    private fun handleErrorJoined(response: Response<String>) {
        when(response.code()){
            201 -> {
                s.makeToast("Successfully Joined!")
                s.observer.notifySubscribers(FuncToRun.GET_USERS)
                s.nav?.popBackStack()
            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast(response.message())
        }
    }

}

