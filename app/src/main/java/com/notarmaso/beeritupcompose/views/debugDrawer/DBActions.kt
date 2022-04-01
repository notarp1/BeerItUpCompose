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
fun DebugDrawer(drawer: DebugDrawerViewModel){

    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) { drawer.navigate(MainActivity.MAIN_MENU) }


        Column() {
            Button(onClick = { drawer.removeUsers()}) {
                Text(text = "Delete Users")
            }

            Button(onClick = {drawer.removeBeers() }) {
                Text(text = "Delete Beers")
            }

            Button(onClick = {drawer.resetUsers() }) {
                Text(text = "Reset Users")
            }
        }
    }
}