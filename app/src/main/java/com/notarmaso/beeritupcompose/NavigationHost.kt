package com.notarmaso.beeritupcompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notarmaso.beeritupcompose.models.SampleData
import com.notarmaso.beeritupcompose.views.*
import org.koin.androidx.compose.get


@Composable
fun NavigationHost(service: Service){
    val navController = rememberNavController()
    service.navHostController = navController

    //Companion object
    val cpm = MainActivity.Companion

    val mainMenuViewModel = get<MainMenuViewModel>()
    val selectBeerViewModel = get<SelectBeerViewModel>()
    val selectUserViewModel = get<SelectUserViewModel>()
    val beerQuantityViewModel = get<BeerQuantityViewModel>()
    //val addUserViewModel = get<AddUserViewModel>()

    NavHost(navController = navController, startDestination = cpm.MAIN_MENU){
        composable(cpm.MAIN_MENU){
            MainMenu(mainMenuViewModel)
        }

        composable(cpm.SELECT_USER){
            SelectUser(selectUserViewModel)
        }

        composable(cpm.SELECT_BEER){
            SelectBeer(selectBeerViewModel)
        }

        composable(cpm.SELECT_BEER_QUANTITY){
            BeerQuantityPage(beerQuantityViewModel)
        }
       /* composable(cpm.ADD_USER){
            AddUser(addUserViewModel)
        }*/
    }
}












