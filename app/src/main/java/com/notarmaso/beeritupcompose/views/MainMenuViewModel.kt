package com.notarmaso.beeritupcompose.views

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.Service
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject


class MainMenuViewModel(serviceVm: Service): ViewModel(), KoinComponent{
    private val service = serviceVm


    fun setPage(page: String){
        service.currentPage = page
    }

    fun navigate(location: String){
        service.navigate(location)
    }
    fun navigateBack(location: String){
        service.navigateBack(location)
    }

}