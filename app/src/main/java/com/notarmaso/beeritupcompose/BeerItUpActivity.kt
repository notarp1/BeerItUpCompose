package com.notarmaso.beeritupcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.room.Room
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.ui.theme.BeerItUpComposeTheme
import com.notarmaso.beeritupcompose.views.BeerQuantityViewModel
import com.notarmaso.beeritupcompose.views.MainMenuViewModel
import com.notarmaso.beeritupcompose.views.SelectBeerViewModel
import com.notarmaso.beeritupcompose.views.SelectUserViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject


class MainActivity : ComponentActivity() {

    companion object{
        const val MAIN_MENU = "mainMenu"
        const val SELECT_USER = "selectUser"
        const val SELECT_BEER = "selectBeer"
        const val SELECT_BEER_QUANTITY = "selectBeerQuantity"
        const val ADD_USER = "addUser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(serviceModule, vmModule)
        }

        val beerService : Service = get(parameters = { parametersOf(applicationContext) })


        setContent {
            BeerItUpComposeTheme {
               Scaffold() {
                   NavigationHost(beerService)
               }
            }
        }
    }
}

















