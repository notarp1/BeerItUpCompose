package com.notarmaso.beeritupcompose.interfaces


import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.views.addSelectBeer.BeerQuantityViewModel
import com.notarmaso.beeritupcompose.views.addSelectBeer.SelectBeerViewModel
import com.notarmaso.beeritupcompose.views.addUser.AddUserViewModel
import com.notarmaso.beeritupcompose.views.debugDrawer.DebugDrawerViewModel
import com.notarmaso.beeritupcompose.views.mainMenu.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.payments.PaymentsViewModel
import com.notarmaso.beeritupcompose.views.userSelection.SelectUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


interface ViewModelFunction{

    fun navigate(location: String)
    fun navigateBack(location: String)
    fun update(page: String)

}

val serviceModule = module {

    single { Observer() }
    single { params -> Service( ctx = params.get(), get()) }
    single { BeerService(get()) }


}

val vmModule = module {
    viewModel { MainMenuViewModel(get()) }
    viewModel { SelectBeerViewModel(get(), get()) }
    viewModel { SelectUserViewModel(get()) }
    viewModel { BeerQuantityViewModel(get(), get()) }
    viewModel { AddUserViewModel(get()) }
    viewModel { DebugDrawerViewModel(get(), get())}
    viewModel { PaymentsViewModel(get())}
}

internal interface UserObserver<T>{
    fun register(subscriber: ViewModelFunction)
    fun remove(subscriber: ViewModelFunction)
    fun notifySubscribers(page: String)
}

