package com.notarmaso.beeritupcompose.views.payments

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.fromJsonToList
import com.notarmaso.beeritupcompose.fromListToJson
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserEntry
import com.notarmaso.beeritupcompose.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState



class PaymentsViewModel(val service: Service): ViewModel(), ViewModelFunction {

    lateinit var user: User


    private var _owedFrom = mutableStateListOf<UserEntry>()
    val owedFrom: List<UserEntry> get() = _owedFrom

    private var _owesTo = mutableStateListOf<UserEntry>()
    val owesTo: List<UserEntry> get() = _owesTo

    private val userRepository: UserRepository = UserRepository(service.context)


    init {
        service.paymentObs.register(this)
    }

    private fun getUserPayments(){
        _owedFrom.clear()
        _owesTo.clear()
        viewModelScope.launch(Dispatchers.IO) {

           user = service.currentUser.let { userRepository.getUser(it.name) }

            val owedFrom: MutableMap<String, Float> = user.owedFrom.fromJsonToList()
            val owesTo: MutableMap<String, Float> = user.owesTo.fromJsonToList()

            createDataObjects(list = owedFrom, true)
            createDataObjects(list = owesTo, false)


        }
    }

    private suspend fun createDataObjects(list: MutableMap<String, Float>, isFrom: Boolean) {

        for ((key, value) in  list.entries) {
            user = userRepository.getUser(key)

            if (isFrom) {
                _owedFrom.add(UserEntry(user.name, value, user.phone))
                return
            }

            _owesTo.add(UserEntry(user.name, value, user.phone))
        }
    }



    override fun navigate(location: String) {

    }

    override fun navigateBack(location: String) {
        service.navigate(location = location)
    }

    override fun update() {
       getUserPayments()
    }

    fun makeTransaction(user: UserEntry) {
        service.createAlertBoxPayment(user) { handleTransaction(user) }
    }

    private fun handleTransaction(user: UserEntry){
        viewModelScope.launch(Dispatchers.IO) {
            /* Handle current user */
            val currentUser = service.currentUser
            val userPayments = currentUser.owesTo.fromJsonToList()

            userPayments[user.name] = 0f


            service.currentUser.owesTo = userPayments.fromListToJson()

            userRepository.updateUser(currentUser)

            /* Handle selected user */
            val selectedUser = userRepository.getUser(user.name)
            val selectedUserPayments = selectedUser.owedFrom.fromJsonToList()

            selectedUserPayments[currentUser.name] = 0f

            selectedUser.owedFrom = selectedUserPayments.fromListToJson()

            userRepository.updateUser(selectedUser)

            getUserPayments()


        }

    }


}
