package com.notarmaso.beeritupcompose.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(showDrawer: Boolean = true){
    val configuration = LocalConfiguration.current
    val extraPadding by animateDpAsState(
        if (showDrawer)  configuration.screenWidthDp.dp else 0.dp
    )
    Box(modifier = Modifier
        .fillMaxHeight()
        .background(Color(0xFF2C3F46))
        .width(extraPadding),){

        if(showDrawer) {
            Column() {
                Button(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(start = 10.dp)
                        .width(130.dp),
                    onClick = {print("NOT IMPLEMENTED")}
                ) {
                    Text("Menu")
                }
                Button(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(130.dp),
                    onClick = {print("NOT IMPLEMENTED")}
                ) {
                    Text("Opret Bruger")
                }
            }

        }

    }

}
