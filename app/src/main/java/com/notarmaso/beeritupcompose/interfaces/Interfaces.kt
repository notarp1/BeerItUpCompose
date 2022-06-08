package com.notarmaso.beeritupcompose.interfaces


import com.notarmaso.beeritupcompose.BeerItUpMainActivityViewModel
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.views.beeritup.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.beeritup.add_beverage.AddBeverageViewModel
import com.notarmaso.beeritupcompose.views.beeritup.join_kitchen.JoinKitchenViewModel
import com.notarmaso.beeritupcompose.views.beeritup.payments.PaymentsViewModel
import com.notarmaso.beeritupcompose.views.beeritup.select_user.SelectUserViewModel
import com.notarmaso.beeritupcompose.views.start_screen.StartMenuViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_kitchen.AddKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_kitchen.LoginKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_user.LoginUserViewModel
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.logbook.LogbookViewModel
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageViewModel
import com.notarmaso.db_access_setup.views.beeritup.statistics.StatisticsViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


interface Observerable{

    fun update(methodToRun: FuncToRun)

}

val serviceModule = module {

    single { Observer() }
    single { params -> StateHandler(ctx = params.get())}
    single { params -> Service( ctx = params.get(), get(), get()) }

}

val vmModule = module {
    viewModel { BeerItUpMainActivityViewModel(get()) }
    viewModel { StartMenuViewModel(get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { JoinKitchenViewModel(get())}
    viewModel { LoginKitchenViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { AddUserViewModel(get()) }
    viewModel { AddKitchenViewModel(get()) }
    viewModel { SelectUserViewModel(get()) }
    viewModel { StatisticsViewModel(get())}
    viewModel { AddBeverageViewModel(get()) }
    viewModel { AddBeverageQuantityViewModel(get())}
    viewModel { SelectBeverageViewModel(get())}
    viewModel { SelectBeverageQuantityViewModel(get())}
    viewModel { PaymentsViewModel(get())}
    viewModel { LogbookViewModel(get())}



}

internal interface UserObserver<T>{
    fun register(subscriber: Observerable)
    fun remove(subscriber: Observerable)
    fun notifySubscribers(funcToRun: FuncToRun)
}

interface Form{
    fun setName(newText: String)
    fun setPass(newText: String)
    fun setPin(newText: String)
    fun setPhone(newText: String)
}
