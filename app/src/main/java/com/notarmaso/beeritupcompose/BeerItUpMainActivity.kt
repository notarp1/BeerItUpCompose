package com.notarmaso.beeritupcompose

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
import com.notarmaso.beeritupcompose.interfaces.serviceModule
import com.notarmaso.beeritupcompose.interfaces.vmModule
import com.notarmaso.beeritupcompose.ui.theme.BeerItUpTheme
import com.notarmaso.beeritupcompose.views.beeritup.MainMenu
import com.notarmaso.beeritupcompose.views.beeritup.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.start_screen.StartMenu
import com.notarmaso.beeritupcompose.views.start_screen.StartMenuViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_kitchen.AddKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_kitchen.LoginKitchenViewModel
import com.notarmaso.beeritupcompose.views.start_screen.login_user.LoginUserViewModel
import com.notarmaso.db_access_setup.views.beeritup.login.LoginKitchen
import com.notarmaso.db_access_setup.views.start_screen.add_kitchen.AddKitchen
import com.notarmaso.db_access_setup.views.start_screen.add_user.AddUser
import com.notarmaso.db_access_setup.views.start_screen.login_user.LoginUser
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
    service.navHostController = navController


    val startMenuViewModel = get<StartMenuViewModel>()
    val mainMenuViewModel = get<MainMenuViewModel>()
    val loginUserViewModel = get<LoginUserViewModel>()
    val loginKitchenViewModel = get<LoginKitchenViewModel>()
    val addKitchenViewModel = get <AddKitchenViewModel>()
    val addUserViewModel = get<AddUserViewModel>()

    AnimatedNavHost(navController = navController,
        startDestination = Pages.MAIN_MENU.value,
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
        composable(Pages.ADD_USER.value){
            AddUser(addUserViewModel)
        }
        composable(Pages.ADD_KITCHEN.value){
            AddKitchen(addKitchenViewModel)
        }



    }
}












