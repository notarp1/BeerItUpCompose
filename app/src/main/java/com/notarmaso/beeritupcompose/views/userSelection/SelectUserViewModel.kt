package com.notarmaso.beeritupcompose.views.userSelection

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import kotlinx.coroutines.*

class SelectUserViewModel(val service: Service): ViewModel(), ViewModelFunction {

    var users by mutableStateOf<List<User>?>(null)
    private val beerRepository: BeerRepository = BeerRepository(Application())
    private val userRepository: UserRepository = UserRepository(Application())

    init {
        service.userObs.register(this)

    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            users = userRepository.getAllUsers()
        }
    }


    override fun navigate(location: String){
        service.navigate(location)
    }


    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update() {
        viewModelScope.launch(Dispatchers.IO){
            getUsers()
        }
    }
}