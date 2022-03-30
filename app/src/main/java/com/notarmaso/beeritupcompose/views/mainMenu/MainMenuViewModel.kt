package com.notarmaso.beeritupcompose.views.mainMenu

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.Service
import org.koin.core.component.KoinComponent


class MainMenuViewModel(val service: Service): ViewModel(){



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