package com.notarmaso.db_access_setup.views.beeritup.join_kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

import com.notarmaso.db_access_setup.ui.theme.LightBlue
import com.notarmaso.db_access_setup.ui.theme.components.ButtonMain
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldName
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPassword
import com.notarmaso.db_access_setup.views.beeritup.NavigationBar

@Composable
fun JoinKitchen(joinKitchenViewModel: JoinKitchenViewModel) {
    Column {
        Box(modifier = Modifier.weight(1f)){
            JoinKitchenForm(joinKitchenViewModel)
        }
        NavigationBar()
    }
}




@Composable
fun JoinKitchenForm(jkVm: JoinKitchenViewModel){
    val configuration = LocalConfiguration.current

    fun width(widthScale: Double): Double { return configuration.screenWidthDp * widthScale }
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }


    Box(modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(Color.White, LightBlue)
            )
        )
        .fillMaxSize(),
        contentAlignment = Alignment.TopCenter){
    
        Column(Modifier.padding(40.dp)) {
           // FormRow("E.g. MyKitchen","Kitchen Name", jkVm)
            TextFieldName(placeholder = "E.g. MyKitchen", vm = jkVm, width =  width(0.66).dp, )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldPassword(placeholder = "********", vm = jkVm, width =  width(0.66).dp)
            Spacer(modifier = Modifier.height(20.dp))
            ButtonMain(onClick = { jkVm.authorizeKitchen() }, text = "Assign" , isInverted = false, widthScale = 0.66 )
          //  ButtonBeerItUp(onClick = { jkVm.authorizeKitchen()}, width = width.dp, txt = "Assign")

        }
    }
}
