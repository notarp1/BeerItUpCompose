package com.notarmaso.beeritupcompose.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.models.User



class AddUserViewModel(serviceVm: Service): ViewModel() {
    var service = serviceVm
    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var owedFrom by mutableStateOf<Map<String, Double>?>(null)
    var owesTo by mutableStateOf<Map<String, Double>?>(null)


    fun navigateBack(location: String){
        service.navigateBack(location)
    }

    fun onSubmit(){
        val user = User(name, phone, null, null)
        println("HAHA:" + user.name)
       // service.db.userDao().insertUser(user)
        //navigateBack(MainActivity.MAIN_MENU)
    }



}