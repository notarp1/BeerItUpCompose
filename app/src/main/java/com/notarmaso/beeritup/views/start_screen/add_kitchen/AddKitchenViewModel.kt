package com.notarmaso.beeritup.views.start_screen.add_kitchen

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.Service
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.interfaces.Form
import com.notarmaso.beeritup.models.KitchenToPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddKitchenViewModel(private val s: Service, private val kitchenRepo: KitchenRepository) : ViewModel(), Form {




    private var _name by mutableStateOf("")
    val name: String get() = _name

    private var _password by mutableStateOf("")
    private val password: String get() = _password

    private var _pin by mutableStateOf("")
    private val pin: String get() = _pin

    private var _email by mutableStateOf("")
    private val email: String get() = _email

    private var _passwordConfirmation by mutableStateOf("")
    private val passwordConfirmation: String get() = _passwordConfirmation


    override fun setName(newText: String, isEmail: Boolean) {
        if (isEmail) _email = newText
        else _name = newText
    }

    override fun setPass(newText: String, isConfirm: Boolean) {
        if (isConfirm) _passwordConfirmation = newText
        else _password = newText
    }

    override fun setPin(newText: String) {
        _pin = newText
    }

    override fun setPhone(newText: String) {
        TODO("Not yet implemented")
    }


    /*First stage of creating user*/

    fun onPressedNext() {
        confirmAndGoToNextStep()
    }


    private fun confirmAndGoToNextStep() {
        viewModelScope.launch {
            if (!nameValidation()) return@launch
            if (!passValidation()) return@launch


            withContext(Dispatchers.IO) {

                val res = kitchenRepo.isNameAvailable(name)
                println("HAHA " + name + "HAHA " + res.body())

                if (res.body() == true) {
                    s.navigate(Pages.ADD_KITCHEN_STEP2)
                } else {
                    s.makeToast("Name aready taken")
                }
            }


        }
    }

    /*First stage of creating user*/

    fun onConfirm() {
        viewModelScope.launch() {
            addKitchen()
        }
    }

    private suspend fun addKitchen() {

        if (!pinValidation()) return
        if (!isValidEmail(email)) return
        try {
            filterWhitespaces()
            val pinToPost = pin.toInt()
            val kitchen = KitchenToPost(name, password, pinToPost, email)

            val res: Response<String>
            withContext(Dispatchers.IO) { res = kitchenRepo.addKitchen(kitchen) }
            if(res.isSuccessful){
                s.makeToast("Kitchen Created!")
                s.logInKitchen(name, password)
                resetTextFields()

            } else s.makeToast(res.message())


        } catch (e: Exception) {
            s.makeToast("Error: Only digits is allowed")
        }


    }




    private fun filterWhitespaces() {
        _email.filter { !it.isWhitespace() }
        _name.filter {  !it.isWhitespace() }

    }

    /*VALIDATIONS*/
    private fun resetTextFields() {
        _name = ""
        _password = ""
        _passwordConfirmation = ""
        _pin = ""
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


    private fun passValidation(): Boolean {
        if (password.length < 6) {
            Toast.makeText(s.context, "Password must be at least 6 digits!", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (password != passwordConfirmation) {
            s.makeToast("Passwords are not the same!")
            return false
        }
        return true
    }

    private fun nameValidation(): Boolean {
        if (name.length < 4) {
            s.makeToast("Name must be longer than 4 characters!")
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



}