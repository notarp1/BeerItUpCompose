package com.notarmaso.beeritupcompose.views.start_screen.add_kitchen

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.interfaces.Form
import com.notarmaso.db_access_setup.models.KitchenToPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddKitchenViewModel(private val service: Service) : ViewModel(), Form {

    private val kitchenRepo = KitchenRepository

    private var _name by mutableStateOf("")
    val name: String get() = _name

    private var _password by mutableStateOf("")
    val password: String get() = _password

    private var _pin by mutableStateOf("")
    val pin: String get() = _pin



    fun addKitchen(){
        if(nameValidation() && passValidation() && pinValidation() ) {
            try {
                val pinToPost = pin.toInt()

                val kitchen = KitchenToPost(name, password, pinToPost)
                viewModelScope.launch() {
                    val res: Response<String>
                    withContext(Dispatchers.IO) { res = kitchenRepo.addKitchen(kitchen) }
                    handleErrorUser(res)


                }

            } catch (e: Exception) {
                service.makeToast("Error: Only digits is allowed")
            }

        }
    }



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
        TODO("Not yet implemented")
    }


    private fun resetTextFields(){
        _name = ""
        _password = ""
        _pin = ""
    }

    private fun passValidation(): Boolean {
        if (password.length < 6) {
            Toast.makeText(service.context, "Password must be at least 6 digits!", Toast.LENGTH_SHORT).show()
            return  false
        }
        return true
    }

    private fun nameValidation(): Boolean {
        if (name.length < 4) {
            Toast.makeText(service.context, "Name must be longer than 4 characters!", Toast.LENGTH_SHORT).show()
            return  false
        }
        return true
    }

    private fun pinValidation(): Boolean {
        if (pin.length in 4..8) {
            return true
        }
        Toast.makeText(service.context, "Pin must be between 4 and 8 digits!", Toast.LENGTH_SHORT).show()
        return  false
    }


    private fun handleErrorUser(response: Response<String>) {
        when(response.code()){
            201 -> {
                service.makeToast("Kitchen Created!")
                resetTextFields()
                service.nav?.popBackStack()
            }

            400 -> service.makeToast("Error: This User Does Not Exist")
            409 -> service.makeToast("Error: This User Already Exist")
            500 -> service.makeToast(response.message())
            else -> service.makeToast("Error: Unknown")
        }
    }
}