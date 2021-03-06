package com.notarmaso.beeritup.views.beeritup.join_kitchen

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritup.ui.theme.components.ButtonMain
import com.notarmaso.beeritup.ui.theme.components.TopBar
import com.notarmaso.beeritup.ui.theme.components.TextFieldName
import com.notarmaso.beeritup.ui.theme.components.TextFieldPassword

@Composable
fun JoinKitchen(joinKitchenViewModel: JoinKitchenViewModel) {

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
        },"join kitchen", goTo = { joinKitchenViewModel.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack,)

        JoinKitchenForm(joinKitchenViewModel, Modifier.constrainAs(forms){

            top.linkTo(topBar.bottom, 80.dp)
            centerHorizontallyTo(parent)

        })

    }


}



/*TODO Funciton below?*/

@Composable
fun JoinKitchenForm(jkVm: JoinKitchenViewModel, modifier: Modifier = Modifier){
    val configuration = LocalConfiguration.current
    fun width(widthScale: Double): Double { return configuration.screenWidthDp * widthScale }


    Box(modifier = modifier){

        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
           // FormRow("E.g. MyKitchen","Kitchen Name", jkVm
            Text(text = "Join Kitchen", style = MaterialTheme.typography.h1, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(40.dp))
            TextFieldName(placeholder = "E.g. MyKitchen", vm = jkVm, width =  width(0.66).dp, )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldPassword(placeholder = "********", vm = jkVm, width =  width(0.66).dp)
            Spacer(modifier = Modifier.height(20.dp))
            ButtonMain(onClick = { jkVm.onAssignPressed() }, text = "Assign" , isInverted = false, widthScale = 0.66 )
          //  ButtonBeerItUp(onClick = { jkVm.authorizeKitchen()}, width = width.dp, txt = "Assign")

        }
    }
}
