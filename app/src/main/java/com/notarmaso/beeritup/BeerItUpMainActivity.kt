package com.notarmaso.beeritup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.notarmaso.beeritup.interfaces.serviceModule
import com.notarmaso.beeritup.interfaces.vmModule
import com.notarmaso.beeritup.ui.theme.BeerItUpTheme
import com.notarmaso.beeritup.views.beeritup.MainMenu
import com.notarmaso.beeritup.views.beeritup.MainMenuViewModel
import com.notarmaso.beeritup.views.beeritup.add_beverage.AddBeverageViewModel
import com.notarmaso.beeritup.views.beeritup.join_kitchen.JoinKitchenViewModel
import com.notarmaso.beeritup.views.beeritup.payments.PaymentsViewModel
import com.notarmaso.beeritup.views.beeritup.select_user.SelectUser
import com.notarmaso.beeritup.views.beeritup.select_user.SelectUserViewModel
import com.notarmaso.beeritup.views.start_screen.StartMenu
import com.notarmaso.beeritup.views.start_screen.StartMenuViewModel
import com.notarmaso.beeritup.views.start_screen.add_kitchen.AddKitchenViewModel
import com.notarmaso.beeritup.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.beeritup.views.start_screen.login_kitchen.LoginKitchenViewModel
import com.notarmaso.beeritup.views.start_screen.login_user.LoginUserViewModel
import com.notarmaso.db_access_setup.dal.DBInstance
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverage
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverageQuantity
import com.notarmaso.db_access_setup.views.beeritup.add_beverage.AddBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.join_kitchen.JoinKitchen
import com.notarmaso.db_access_setup.views.beeritup.logbook.Logbook
import com.notarmaso.db_access_setup.views.beeritup.logbook.LogbookViewModel
import com.notarmaso.beeritup.views.start_screen.login_kitchen.LoginKitchen
import com.notarmaso.db_access_setup.views.beeritup.payments.Payments
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverage
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageQuantity
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageQuantityViewModel
import com.notarmaso.db_access_setup.views.beeritup.select_beverage.SelectBeverageViewModel
import com.notarmaso.db_access_setup.views.beeritup.statistics.Statistics
import com.notarmaso.db_access_setup.views.beeritup.statistics.StatisticsViewModel
import com.notarmaso.db_access_setup.views.start_screen.add_kitchen.AddKitchen
import com.notarmaso.db_access_setup.views.start_screen.add_kitchen.AddKitchen_Step_2
import com.notarmaso.db_access_setup.views.start_screen.add_user.AddUser
import com.notarmaso.db_access_setup.views.start_screen.add_user.Add_User_Step_2
import com.notarmaso.beeritup.views.start_screen.login_user.LoginUser
import org.koin.android.ext.android.get
import org.koin.androidx.compose.get
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.startKoin {
            modules(serviceModule, vmModule)
        }
       // val observer : ObserverNotifier = get()
        val dbInstance: DBInstance = get(parameters = { parametersOf(this) })
        val stateHandler: StateHandler = get(parameters = { parametersOf(this)})
        val beerService : Service = get(parameters = { parametersOf(this)})
        val mainVieModel: BeerItUpMainActivityViewModel = get()

        installSplashScreen().apply {
            setKeepVisibleCondition{mainVieModel.isLoading.value}
        }
        setContent {
            BeerItUpTheme {
                Scaffold() {
                    NavigationHost(beerService)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(service: Service){
    val navController = rememberAnimatedNavController()
    service.nav = navController


    val startMenuViewModel = get<StartMenuViewModel>()
    val mainMenuViewModel = get<MainMenuViewModel>()
    val joinKitchenViewModel = get<JoinKitchenViewModel>()
    val loginUserViewModel = get<LoginUserViewModel>()
    val loginKitchenViewModel = get<LoginKitchenViewModel>()
    val addKitchenViewModel = get <AddKitchenViewModel>()
    val addUserViewModel = get<AddUserViewModel>()

    val selectUserViewModel = get<SelectUserViewModel>()
    val selectBeverageViewModel = get<SelectBeverageViewModel>()
    val selectBeverageQuantityViewModel = get<SelectBeverageQuantityViewModel>()
    val addBeverageViewModel = get<AddBeverageViewModel>()
    val addBeverageQuantityViewModel = get<AddBeverageQuantityViewModel>()

    val paymentsViewModel = get<PaymentsViewModel>()
    val logbookViewModel = get<LogbookViewModel>()
    val statisticsViewModel = get<StatisticsViewModel>()

    AnimatedNavHost(navController = navController,
        startDestination = Pages.START_MENU.value,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200)) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(200))},
        popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(200)) }
    ){

        composable(Pages.START_MENU.value){
            StartMenu(startMenuViewModel)
        }
        composable(Pages.MAIN_MENU.value){
            MainMenu(mainMenuViewModel)
        }
        composable(Pages.LOGIN_KITCHEN.value){
            LoginKitchen(loginKitchenViewModel)
        }
        composable(Pages.LOGIN_USER.value){
            LoginUser(loginUserViewModel )
        }
        composable(Pages.JOIN_KITCHEN.value){
            JoinKitchen(joinKitchenViewModel)
        }
        composable(Pages.ADD_USER.value){
            AddUser(addUserViewModel)
        }
        composable(Pages.ADD_USER_STEP2.value){
            Add_User_Step_2(addUserViewModel)
        }
        composable(Pages.ADD_KITCHEN.value){
            AddKitchen(addKitchenViewModel)
        }
        composable(Pages.ADD_KITCHEN_STEP2.value){
            AddKitchen_Step_2(addKitchenViewModel)
        }
        composable(Pages.LOGBOOKS.value){
            Logbook(logbookViewModel)
        }
        composable(Pages.PAYMENTS.value){
            Payments(paymentsViewModel)
        }
        composable(Pages.STATISTICS.value){
            Statistics(statisticsViewModel)
        }
        composable(Pages.USER_SELECTION.value){
            SelectUser(selectUserViewModel)
        }
        composable(Pages.ADD_BEVERAGE_STAGE_1.value){
            AddBeverage(addBeverageViewModel)
        }
        composable(Pages.ADD_BEVERAGE_STAGE_2.value){
            AddBeverageQuantity(addBeverageQuantityViewModel)
        }
        composable(Pages.SELECT_BEVERAGE_STAGE_1.value){
            SelectBeverage(selectBeverageViewModel)
        }
        composable(Pages.SELECT_BEVERAGE_STAGE_2.value){
            SelectBeverageQuantity(selectBeverageQuantityViewModel)
        }





    }
}












