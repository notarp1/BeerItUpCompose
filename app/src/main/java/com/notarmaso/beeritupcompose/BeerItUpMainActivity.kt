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
import com.notarmaso.beeritupcompose.views.debugDrawer.*
import com.notarmaso.beeritupcompose.views.logBooks.LogBook
import com.notarmaso.beeritupcompose.views.logBooks.LogBookViewModel
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
import kotlin.math.log

class MainActivity : ComponentActivity() {



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



    val mainMenuViewModel = get<MainMenuViewModel>()
    val selectBeerViewModel = get<SelectBeerViewModel>()
    val selectUserViewModel = get<SelectUserViewModel>()
    val beerQuantityViewModel = get<BeerQuantityViewModel>()
    val addUserViewModel = get<AddUserViewModel>()
    val debugDrawerViewModel = get<DebugDrawerViewModel>()
    val paymentsViewModel = get<PaymentsViewModel>()
    val logBookViewModel = get<LogBookViewModel>()

    NavHost(navController = navController, startDestination = Pages.MAIN_MENU.value){
        composable(Pages.MAIN_MENU.value){
            MainMenu(mainMenuViewModel)
        }

        composable(Pages.SELECT_USER.value){
            SelectUser(selectUserViewModel)
        }

        composable(Pages.BUY_BEER.value){
            SelectBeer(selectBeerViewModel)
        }

        composable(Pages.SELECT_BEER_QUANTITY.value){
            BeerQuantityPage(beerQuantityViewModel)
        }
        composable(Pages.ADD_USER.value){
            AddUser(addUserViewModel)
        }
        composable(Pages.DEBUG_DRAWER.value){
            DebugDrawer(debugDrawerViewModel)
        }
        composable(Pages.PAYMENTS.value){
            Payments(paymentsViewModel)
        }
        composable(Pages.LOG_BOOKS.value){
            LogBook(logBookViewModel = logBookViewModel)
        }
        composable(Pages.PIN_PAD.value){
            DebugPinAccess(drawer = debugDrawerViewModel)
        }
        composable(Pages.DELETE_USER.value){
            DeleteUser(drawer = debugDrawerViewModel)
        }
        composable(Pages.EDIT_USER.value){
            EditUser(drawer = debugDrawerViewModel)
        }
    }
}












