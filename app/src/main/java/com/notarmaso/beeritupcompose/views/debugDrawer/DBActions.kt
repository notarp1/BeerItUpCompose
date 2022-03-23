package com.notarmaso.beeritupcompose.views.debugDrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DonutLarge
import androidx.compose.runtime.Composable
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.views.mainMenu.MainMenuContents

@Composable
fun DebugDrawer(service: Service){

    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) { service.navigate(MainActivity.MAIN_MENU) }


        Column() {
            Button(onClick = { service.removeUsers()}) {
                Text(text = "Delete Users")
            }

            Button(onClick = {service.removeBeers() }) {
                Text(text = "Delete Beers")
            }
        }
    }
}