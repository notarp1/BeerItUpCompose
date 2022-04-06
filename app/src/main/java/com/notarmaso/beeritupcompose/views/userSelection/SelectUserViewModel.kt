package com.notarmaso.beeritupcompose.views.userSelection

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import kotlinx.coroutines.*

class SelectUserViewModel(val service: Service): ViewModel(), ViewModelFunction {

    var _users by mutableStateOf<List<User>>(listOf())
    val users: List<User> get() = _users

    private val userRepository: UserRepository = UserRepository(Application())

    init {
        service.observer.register(this)

    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _users = userRepository.getAllUsers()
        }
    }


    override fun navigate(location: String){
        service.navigate(location)
    }


    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update(page: String) {
        if(isSelectingUser(page)){
        viewModelScope.launch(Dispatchers.IO){
            getUsers()
        }
        }
    }

    private fun isSelectingUser(page: String): Boolean{
        return page == MainActivity.SELECT_USER
    }
}