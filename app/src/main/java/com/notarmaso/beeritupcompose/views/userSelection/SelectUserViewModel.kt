package com.notarmaso.beeritupcompose.views.userSelection

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromJsonToListInt
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import kotlinx.coroutines.*

class SelectUserViewModel(val service: Service): ViewModel(), ViewModelFunction {

    private var _users by mutableStateOf<List<User>>(listOf())

    val users: List<User> get() = _users


    private val userRepository: UserRepository = UserRepository(Application())

    init {
        service.observer.register(this)

    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempUsers: MutableList<User> = userRepository.getAllUsers()

            tempUsers.sortByDescending {user -> user.totalBeers.fromJsonToListInt()[service.currentDate] }

            _users = tempUsers

        }
    }


    override fun navigate(location: Pages){
        service.navigate(location)
    }


    override fun navigateBack(location: Pages){
        service.navigateBack(location)
    }

    override fun update(page: String) {
        if(page == Pages.SELECT_USER.value){
            viewModelScope.launch(Dispatchers.IO){
                getUsers()
            }
        }
    }


}