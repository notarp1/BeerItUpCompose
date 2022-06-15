package com.notarmaso.beeritup.views.start_screen.add_user

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.FuncToRun
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.StateHandler
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.interfaces.Form
import com.notarmaso.beeritup.models.UserRecieve
import com.notarmaso.beeritup.models.UserToPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class AddUserViewModel(
    private val s: Service,
    private val kitchenRepo: KitchenRepository,
    private val userRepo: UserRepository
) : ViewModel(), Form {



    private var _name by mutableStateOf("")
    val name: String get() = _name

    private var _phone by mutableStateOf("")
    val phone: String get() = _phone

    private var _email by mutableStateOf("")
    val email: String get() = _email

    private var _password by mutableStateOf("")
    val password: String get() = _password


    private var _passconfirmation by mutableStateOf("")
    val passconfirmation: String get() = _passconfirmation

    private var _pin by mutableStateOf("")
    val pin: String get() = _pin


    override fun setName(newText: String, isEmail: Boolean) {
        if (isEmail) _email = newText
        else _name = newText
    }

    override fun setPass(newText: String, isPasswordConfirm: Boolean) {
        if (isPasswordConfirm) _passconfirmation = newText
        else _password = newText
    }

    override fun setPin(newText: String) {
        _pin = newText
    }

    override fun setPhone(newText: String) {
        _phone = newText
    }


    /*USER CREATION*/

    fun onPressedNext() {
        confirmAndGoToNextStep()
    }


    private fun confirmAndGoToNextStep() {
        viewModelScope.launch {
            if (!nameValidation()) return@launch
            if (!isValidEmail(email)) return@launch
            if (!phoneValidation()) return@launch
            withContext(Dispatchers.IO) {
                val res = userRepo.isEmailAvailable(email)
                if (res.body() == true) {
                    filterWhitespaces()
                    s.navigate(Pages.ADD_USER_STEP2)
                } else {
                    s.makeToast("Email aready taken")
                }
            }


        }
    }

    fun onConfirm() {
        viewModelScope.launch() {
            createUser()
        }
    }

    private suspend fun createUser() {

        if (passValidation() && pinValidation()) {
            try {
                val pinToPost = pin.toInt()
                if (phoneValidation()) {
                    val user = UserToPost(
                        name,
                        phone,
                        password,
                        pinToPost,
                        email
                    )

                    val res: Response<UserRecieve>
                    withContext(Dispatchers.IO) { res = userRepo.addUser(user) }
                    errorHandlingCreateUser(res)

                }

            } catch (e: Exception) {
                s.makeToast("Error: Only digits is allowed")
            }
        }
    }


    private fun errorHandlingCreateUser(response: Response<UserRecieve>) {
        when (response.code()) {
            201 -> {
                s.makeToast("User Created!")
                when (s.stateHandler.appMode) {
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
            409 -> s.makeToast("Error: A user with this email already exist")
            500 -> s.makeToast(response.message())
            else -> s.makeToast("Error: Unknown")
        }
    }

    private fun logInUser() {
        s.logInUser(email, password)
        resetTextFields()
    }

    private fun addUserToKitchen(id: Int) {
        val state = s.stateHandler.appMode as StateHandler.AppMode.SignedInAsKitchen
        handleKitchenRegistration(state, id)

    }


    /* JOIN KITCHEN IF USER IS CREATED FROM KITCHEN ACC*/
    private fun handleKitchenRegistration(state: StateHandler.AppMode.SignedInAsKitchen, id: Int) {
        viewModelScope.launch {
            val response: Response<String>
            withContext(Dispatchers.IO) {
                response = kitchenRepo.addKitchenUser(state.kId, id)
            }
            handleErrorJoined(response)
        }
    }


    private fun handleErrorJoined(response: Response<String>) {
        when (response.code()) {
            201 -> {
                s.makeToast("Successfully Joined!")
                s.observer.notifySubscribers(FuncToRun.GET_USERS)
                s.navigateAndClearBackstack(Pages.MAIN_MENU)
            }
            500 -> s.makeToast(response.message())
            else -> s.makeToast(response.message())
        }
    }


    /* VALIDATION */

    private fun filterWhitespaces() {
        _email.filter { !it.isWhitespace() }
        _phone.filter { !it.isWhitespace() }

    }

    private fun passValidation(): Boolean {
        if (password.length < 6) {
            s.makeToast("Password must be at least 6 digits!")
            return false
        }
        if (password != passconfirmation) {
            s.makeToast("Passwords are not the same!")
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
        if (name.length >= 30) {
            Toast.makeText(
                s.context,
                "Name must cant be longer than 30 characters!",
                Toast.LENGTH_SHORT
            )
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
        _passconfirmation
        _pin = ""
        _phone = ""
        _email = ""
    }


    private fun isValidEmail(target: CharSequence?): Boolean {
        if (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            return true
        } else {
            s.makeToast("Please enter a valid email")
            return false
        }
    }


}

