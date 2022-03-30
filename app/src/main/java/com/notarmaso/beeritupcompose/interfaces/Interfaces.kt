package com.notarmaso.beeritupcompose.interfaces


import com.notarmaso.beeritupcompose.BeerObserverNotifier
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.UserObserverNotifier
import com.notarmaso.beeritupcompose.views.addSelectBeer.BeerQuantityViewModel
import com.notarmaso.beeritupcompose.views.addSelectBeer.SelectBeerViewModel
import com.notarmaso.beeritupcompose.views.addUser.AddUserViewModel
import com.notarmaso.beeritupcompose.views.debugDrawer.DebugDrawerViewModel
import com.notarmaso.beeritupcompose.views.mainMenu.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.userSelection.SelectUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


interface ViewModelFunction{

    fun navigate(location: String)
    fun navigateBack(location: String)
    fun update()

}

val serviceModule = module {

    single { UserObserverNotifier() }
    single { BeerObserverNotifier() }
    single { params -> Service( ctx = params.get(), get()) }
    single { BeerService(get(), get()) }
}

val vmModule = module {
    viewModel { MainMenuViewModel(get()) }
    viewModel { SelectBeerViewModel(get(), get()) }
    viewModel { SelectUserViewModel(get()) }
    viewModel { BeerQuantityViewModel(get(), get()) }
    viewModel { AddUserViewModel(get()) }
    viewModel { DebugDrawerViewModel(get(), get())}
}

internal interface UserObserver<T>{
    fun register(subscriber: ViewModelFunction)
    fun remove(subscriber: ViewModelFunction)
    fun notifySubscribers()
}

internal interface BeerObserver<T>{
    fun register(subscriber: ViewModelFunction)
    fun remove(subscriber: ViewModelFunction)
    fun notifySubscribers()
}
