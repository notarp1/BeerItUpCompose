package com.notarmaso.beeritupcompose.views.payments

import android.app.Service
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.fromJsonToList
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(val service: com.notarmaso.beeritupcompose.Service): ViewModel(), ViewModelFunction {

    var user: User? = null
    var owedFrom: MutableMap<String, Float>? = mutableMapOf()
    var owesTo: MutableMap<String, Float>? = mutableMapOf()


    init {
        service.paymentObs.register(this)
    }

    private fun getUser(){
        owedFrom = mutableMapOf()
        owesTo = mutableMapOf()

        viewModelScope.launch(Dispatchers.IO) {
            user = service.currentUser?.let { service.db.userDao().getUser(it.name) }
        }

        if(user?.owedFrom != null) owedFrom = user?.owedFrom?.fromJsonToList()
        if(user?.owesTo != null) owesTo = user?.owesTo?.fromJsonToList()
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