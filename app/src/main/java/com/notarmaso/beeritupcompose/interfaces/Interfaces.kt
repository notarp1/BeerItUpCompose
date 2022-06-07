package com.notarmaso.beeritupcompose.interfaces


import com.notarmaso.beeritupcompose.BeerItUpMainActivityViewModel
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.views.beeritup.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.start_screen.StartMenuViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_kitchen.AddKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_kitchen.LoginKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_user.LoginUserViewModel

import com.notarmaso.db_access_setup.StateHandler
import com.notarmaso.db_access_setup.views.beeritup.MainMenuViewModel
import com.notarmaso.db_access_setup.views.start_screen.StartMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


interface ViewModelFunction{

    fun navigate(location: Pages)
    fun navigateBack(location: Pages)
    fun update(page: String)

}

val serviceModule = module {

    single { Observer() }
    single { params -> StateHandler(ctx = params.get())}
    single { params -> Service( ctx = params.get(), get(), get()) }
    single { BeerService(get()) }


}

val vmModule = module {
    viewModel { StartMenuViewModel(get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { BeerItUpMainActivityViewModel(get()) }
    viewModel { LoginKitchenViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { AddUserViewModel(get()) }
    viewModel { AddKitchenViewModel(get()) }


}

internal interface UserObserver<T>{
    fun register(subscriber: ViewModelFunction)
    fun remove(subscriber: ViewModelFunction)
    fun notifySubscribers(page: String)
}

interface Form{
    fun setName(newText: String)
    fun setPass(newText: String)
    fun setPin(newText: String)
    fun setPhone(newText: String)
}
