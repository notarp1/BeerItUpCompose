package com.notarmaso.beeritup.interfaces


import com.notarmaso.beeritup.BeerItUpMainActivityViewModel
import com.notarmaso.beeritup.*
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.views.beeritup.MainMenuViewModel
import com.notarmaso.beeritup.views.beeritup.add_beverage.AddBeverageViewModel
import com.notarmaso.beeritup.views.beeritup.join_kitchen.JoinKitchenViewModel
import com.notarmaso.beeritup.views.beeritup.payments.PaymentsViewModel
import com.notarmaso.beeritup.views.beeritup.select_user.SelectUserViewModel
import com.notarmaso.beeritup.views.start_screen.StartMenuViewModel
import com.notarmaso.beeritup.views.start_screen.add_kitchen.AddKitchenViewModel
import com.notarmaso.beeritup.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.beeritup.views.start_screen.login_kitchen.LoginKitchenViewModel
import com.notarmaso.beeritup.views.start_screen.login_user.LoginUserViewModel
import com.notarmaso.db_access_setup.dal.DBInstance
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.logbook.LogbookViewModel
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageViewModel
import com.notarmaso.db_access_setup.views.beeritup.statistics.StatisticsViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


interface Observable{

    fun update(funcToRun: FuncToRun)

}

val serviceModule = module {

    single { Observer() }
    single { DBInstance(get())}
    single { KitchenRepository(get())}
    single { UserRepository(get())}
    single { params -> StateHandler(ctx = params.get(), get(), get(), get())}
    single { params -> Service( ctx = params.get(), get(), get(), get(), get()) }

}

val vmModule = module {
    viewModel { BeerItUpMainActivityViewModel(get()) }
    viewModel { StartMenuViewModel(get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { JoinKitchenViewModel(get(), get())}
    viewModel { LoginKitchenViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { AddUserViewModel(get(), get(), get()) }
    viewModel { AddKitchenViewModel(get(), get()) }
    viewModel { SelectUserViewModel(get(), get()) }
    viewModel { StatisticsViewModel(get(), get())}
    viewModel { AddBeverageViewModel(get(), get()) }
    viewModel { AddBeverageQuantityViewModel(get(), get())}
    viewModel { SelectBeverageViewModel(get(), get())}
    viewModel { SelectBeverageQuantityViewModel(get(), get())}
    viewModel { PaymentsViewModel(get(), get())}
    viewModel { LogbookViewModel(get(), get())}



}

internal interface UserObserver<T>{
    fun register(subscriber: Observable)
    fun remove(subscriber: Observable)
    fun notifySubscribers(funcToRun: FuncToRun)
}

interface Form{
    fun setName(newText: String, isEmail: Boolean)
    fun setPass(newText: String, isPasswordConfirm: Boolean)
    fun setPin(newText: String)
    fun setPhone(newText: String)

}
