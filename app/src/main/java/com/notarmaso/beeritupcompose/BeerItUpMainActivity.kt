package com.notarmaso.beeritupcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notarmaso.beeritupcompose.interfaces.serviceModule
import com.notarmaso.beeritupcompose.interfaces.vmModule
import com.notarmaso.beeritupcompose.ui.theme.BeerItUpComposeTheme
import com.notarmaso.beeritupcompose.views.addSelectBeer.BeerQuantityPage
import com.notarmaso.beeritupcompose.views.addSelectBeer.BeerQuantityViewModel
import com.notarmaso.beeritupcompose.views.addSelectBeer.SelectBeer
import com.notarmaso.beeritupcompose.views.addSelectBeer.SelectBeerViewModel
import com.notarmaso.beeritupcompose.views.addUser.AddUser
import com.notarmaso.beeritupcompose.views.addUser.AddUserViewModel
import com.notarmaso.beeritupcompose.views.debugDrawer.DebugDrawer
import com.notarmaso.beeritupcompose.views.debugDrawer.DebugDrawerViewModel
import com.notarmaso.beeritupcompose.views.mainMenu.MainMenu
import com.notarmaso.beeritupcompose.views.mainMenu.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.payments.Payments
import com.notarmaso.beeritupcompose.views.payments.PaymentsViewModel
import com.notarmaso.beeritupcompose.views.userSelection.SelectUser
import com.notarmaso.beeritupcompose.views.userSelection.SelectUserViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.compose.get
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    companion object{
        const val MAIN_MENU = "mainMenu"
        const val SELECT_USER = "selectUser"
        const val BUY_BEER = "buyBeer"
        const val SELECT_BEER_QUANTITY = "selectBeerQuantity"
        const val ADD_USER = "addUser"
        const val DEBUG_DRAWER = "debugDrawer"
        const val ADD_BEER = "addBeer"
        const val PAYMENTS = "payments"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.startKoin {
            modules(serviceModule, vmModule)
        }
       // val observer : ObserverNotifier = get()
        val beerService : Service = get(parameters = { parametersOf(this)})

        setContent {
            BeerItUpComposeTheme {
                Scaffold() {
                    NavigationHost(beerService)
                }
            }
        }
    }
}

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
    val addUserViewModel = get<AddUserViewModel>()
    val debugDrawerViewModel = get<DebugDrawerViewModel>()
    val paymentsViewModel = get<PaymentsViewModel>()

    NavHost(navController = navController, startDestination = cpm.MAIN_MENU){
        composable(cpm.MAIN_MENU){
            MainMenu(mainMenuViewModel)
        }

        composable(cpm.SELECT_USER){
            SelectUser(selectUserViewModel)
        }

        composable(cpm.BUY_BEER){
            SelectBeer(selectBeerViewModel)
        }

        composable(cpm.SELECT_BEER_QUANTITY){
            BeerQuantityPage(beerQuantityViewModel)
        }
        composable(cpm.ADD_USER){
            AddUser(addUserViewModel)
        }
        composable(cpm.DEBUG_DRAWER){
            DebugDrawer(debugDrawerViewModel)
        }
        composable(cpm.PAYMENTS){
            Payments(paymentsViewModel)
        }
    }
}












