package com.notarmaso.beeritupcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BeerItUpMainActivityViewModel(service: Service): ViewModel(), ViewModelFunction {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        service.observer.register(this)
        viewModelScope.launch {

            delay(50)
            service.observer.notifySubscribers("init")


        }


    }

    override fun navigate(location: Pages) {
        TODO("Not yet implemented")
    }

    override fun navigateBack(location: Pages) {
        TODO("Not yet implemented")
    }

    override fun update(page: String) {
        if(page == "finishedLoading"){
            _isLoading.value = false
        }

    }
}