package com.notarmaso.db_access_setup.views.beeritup.join_kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritupcompose.ui.theme.LightBlue
import com.notarmaso.beeritupcompose.ui.theme.components.ButtonMain
import com.notarmaso.beeritupcompose.ui.theme.components.TopBar
import com.notarmaso.beeritupcompose.views.beeritup.join_kitchen.JoinKitchenViewModel
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldName
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPassword

@Composable
fun JoinKitchen(joinKitchenViewModel: JoinKitchenViewModel) {
    val vm = joinKitchenViewModel

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.onPrimary
                    )
                ), alpha = 0.9f
            )
    ) {
        val (forms, topBar) = createRefs()

        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        },"join kitchen", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack,)

        JoinKitchenForm(joinKitchenViewModel, Modifier.constrainAs(forms){

            top.linkTo(topBar.bottom)
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)
        })

    }


}



/*TODO Funciton below?*/

@Composable
fun JoinKitchenForm(jkVm: JoinKitchenViewModel, modifier: Modifier = Modifier){
    val configuration = LocalConfiguration.current

    fun width(widthScale: Double): Double { return configuration.screenWidthDp * widthScale }
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }


    Box(modifier = modifier){

        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
           // FormRow("E.g. MyKitchen","Kitchen Name", jkVm
            Text(text = "Join Kitchen", style = MaterialTheme.typography.h1, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(40.dp))
            TextFieldName(placeholder = "E.g. MyKitchen", vm = jkVm, width =  width(0.66).dp, )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldPassword(placeholder = "********", vm = jkVm, width =  width(0.66).dp)
            Spacer(modifier = Modifier.height(20.dp))
            ButtonMain(onClick = { jkVm.authorizeKitchen() }, text = "Assign" , isInverted = false, widthScale = 0.66 )
          //  ButtonBeerItUp(onClick = { jkVm.authorizeKitchen()}, width = width.dp, txt = "Assign")

        }
    }
}
