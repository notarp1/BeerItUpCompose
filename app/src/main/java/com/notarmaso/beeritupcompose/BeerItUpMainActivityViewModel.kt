package com.notarmaso.beeritupcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.interfaces.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BeerItUpMainActivityViewModel(service: Service): ViewModel(), Observable {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {

        service.observer.register(this)

        viewModelScope.launch {
            //Delay for insuring other viewmodels have been initialized

            delay(100)
            service.observer.notifySubscribers(FuncToRun.GET_LOGIN_STATE)


        }


    }


    override fun update(funcToRun: FuncToRun) {
        if(funcToRun == FuncToRun.FINISHED_LOADING ){
            _isLoading.value = false
        }

    }
}