package com.notarmaso.beeritupcompose

import android.content.Context
import androidx.navigation.NavHostController
import androidx.room.Room
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.views.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val serviceModule = module {
  single { params -> Service( ctx = params.get()) }
}

val vmModule = module {
  viewModel { MainMenuViewModel(get()) }
  viewModel { SelectBeerViewModel(get()) }
  viewModel { SelectUserViewModel(get()) }
  viewModel { BeerQuantityViewModel(get()) }
  viewModel { AddUserViewModel(get())}
}


class Service(ctx: Context) {


  var currentUser: User? = null
  var selectedGlobalBeer: GlobalBeer? = null
  var currentPage: String? = null
  var navHostController: NavHostController? = null

  val db = Room.databaseBuilder(
              ctx,
              AppDatabase::class.java, "BeerItUpDB"
          ).build()

  fun navigate(location: String){
    navHostController?.navigate(location)
  }

  fun navigateBack(location: String){
    navHostController?.navigate(location){popUpTo(location)}
  }
}


