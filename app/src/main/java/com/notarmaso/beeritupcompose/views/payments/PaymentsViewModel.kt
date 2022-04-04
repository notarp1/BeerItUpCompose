package com.notarmaso.beeritupcompose.views.payments

import android.app.Service
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromJsonToList
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(val service: com.notarmaso.beeritupcompose.Service): ViewModel(), ViewModelFunction {

    var user: User? = null


    var owedFrom: MutableMap<String, Float>? by mutableStateOf(mutableMapOf())
    var owesTo: MutableMap<String, Float>? by mutableStateOf(mutableMapOf())

    private val userRepository: UserRepository = UserRepository(service.context)


    init {
        service.paymentObs.register(this)
    }

    private fun getUser(){

        viewModelScope.launch(Dispatchers.IO) {
            user = service.currentUser?.let { userRepository.getUser(it.name) }


        owedFrom = user?.owedFrom?.fromJsonToList()
        owesTo = user?.owesTo?.fromJsonToList()
        }
    }

    override fun navigate(location: String) {

    }

    override fun navigateBack(location: String) {
        service.navigate(location = location)
    }

    override fun update() {
       getUser()
    }


}