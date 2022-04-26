package com.notarmaso.beeritupcompose.views.payments

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import timber.log.Timber
import kotlin.math.log


class PaymentsViewModel(val service: Service): ViewModel(), ViewModelFunction {

    lateinit var user: User


    private var _owedFrom = mutableStateListOf<UserEntry>()
    val owedFrom: List<UserEntry> get() = _owedFrom

    private var _owesTo = mutableStateListOf<UserEntry>()
    val owesTo: List<UserEntry> get() = _owesTo

    private val userRepository: UserRepository = UserRepository(service.context)


    init {
        service.observer.register(this)
    }

    private fun getUserPayments(){
        _owedFrom.clear()
        _owesTo.clear()



        viewModelScope.launch {

           user = service.currentUser.let { userRepository.getUser(it.name) }

            val owedFrom: MutableMap<String, Float> = user.owedFrom.fromJsonToListFloat()
            val owesTo: MutableMap<String, Float> = user.owesTo.fromJsonToListFloat()

            createDataObjects(list = owedFrom, true)
            createDataObjects(list = owesTo, false)


        }
    }

    private suspend fun createDataObjects(list: MutableMap<String, Float>, isFrom: Boolean) {

        for ((key, value) in  list.entries) {
            try {
                user = userRepository.getUser(key)
            }catch (e: Exception){
                Timber.d("Error: $e")
            }

            if (isFrom) {
                _owedFrom.add(UserEntry(user.name, value, user.phone))

            } else {
                _owesTo.add(UserEntry(user.name, value, user.phone))
            }
        }
    }



    override fun navigate(location: Pages) {

    }

    override fun navigateBack(location: Pages) {
        service.navigateBack(location = location)
    }

    override fun update(page: String) {
       if(page == Pages.PAYMENTS.value) {
           getUserPayments()
       }
    }

    fun makeTransaction(user: UserEntry) {
        service.createAlertBoxPayment(user) { handleTransaction(user) }
    }

    private fun handleTransaction(user: UserEntry){
        val currDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val date: String =  currDate.dayOfMonth.toString()  + "/" + currDate.monthNumber + "/" + currDate.year
        viewModelScope.launch() {
            /* Handle current user */
            val currentUser = service.currentUser
            val userPayments = currentUser.owesTo.fromJsonToListFloat()
            val currUserLog = currentUser.transactionsLog.fromJsonToList()

            userPayments[user.name] = 0f
            currentUser.owesTo = userPayments.fromListFloatToJson()

            currUserLog.add("You paid ${user.name} ${user.price}DKK at $date")
            currentUser.transactionsLog = currUserLog.fromListToJson()

            userRepository.updateUser(currentUser)

            /* Handle selected user */
            val selectedUser = userRepository.getUser(user.name)
            val selectedUserPayments = selectedUser.owedFrom.fromJsonToListFloat()
            val selectedUserLog = selectedUser.transactionsLog.fromJsonToList()

            selectedUserPayments[currentUser.name] = 0f
            selectedUser.owedFrom = selectedUserPayments.fromListFloatToJson()

            selectedUserLog.add("${currentUser.name} paid you ${user.price}DKK at $date")
            selectedUser.transactionsLog = selectedUserLog.fromListToJson()


            userRepository.updateUser(selectedUser)

            getUserPayments()


        }

    }


}
