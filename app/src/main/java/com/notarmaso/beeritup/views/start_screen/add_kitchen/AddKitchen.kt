package com.notarmaso.db_access_setup.views.start_screen.add_kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritup.R
import com.notarmaso.beeritup.ui.theme.components.SelectionHeader
import com.notarmaso.beeritup.ui.theme.components.SubmitButton
import com.notarmaso.beeritup.views.start_screen.add_kitchen.AddKitchenViewModel

import com.notarmaso.db_access_setup.ui.theme.components.*

@Composable
fun AddKitchen(addKitchenViewModel: AddKitchenViewModel) {
    val configuration = LocalConfiguration.current
    fun width(widthScale: Double): Double { return configuration.screenWidthDp * widthScale }
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.background,
                    MaterialTheme.colors.onPrimary
                ))) ) {

        val submitButton = createRef()
        val (uNameTF, uPassTF, uPassTF2) = createRefs()
        val (usernameTxt, passTxt, passTxt2) = createRefs()
        val header = createRef()

        val g1 = createGuidelineFromStart(10.dp)
        val g2 = createGuidelineFromEnd(10.dp)

        val txtMargin = 20.dp
        val tfMargin = 20.dp
        val tfHeight = 60.dp

        Text(text = stringResource(R.string.createKitchenHeader),  fontSize = 38.sp, textDecoration = TextDecoration.Underline, style =  MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(header){
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, 80.dp)
            })

        SelectionHeader(text = stringResource(R.string.createKitchenName), true, modifier = Modifier.constrainAs(usernameTxt){
            top.linkTo(header.bottom, margin = 30.dp)
            centerHorizontallyTo(parent)

        })

        TextFieldName(stringResource(R.string.createKitchenNamePlaceholder), Modifier.constrainAs(uNameTF){
            centerHorizontallyTo(parent)
            top.linkTo(usernameTxt.bottom, tfMargin)
        }, addKitchenViewModel,  width(0.66).dp, tfHeight)


        SelectionHeader(text = stringResource(R.string.sharedPassword), true, modifier = Modifier.constrainAs(passTxt){
            top.linkTo(uNameTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPassword(stringResource(R.string.min6chars_placeholder), Modifier.constrainAs(uPassTF){
            centerHorizontallyTo(parent)
            top.linkTo(passTxt.bottom, tfMargin)
        }, addKitchenViewModel,  width(0.66).dp, tfHeight)


        SelectionHeader(text = stringResource(R.string.repeatPass), true, modifier = Modifier.constrainAs(passTxt2){
            top.linkTo(uPassTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPassword(stringResource(R.string.min6chars_placeholder), Modifier.constrainAs(uPassTF2){
            centerHorizontallyTo(parent)
            top.linkTo(passTxt2.bottom, tfMargin)
        }, addKitchenViewModel,  width(0.66).dp, tfHeight, true)





        SubmitButton(Modifier.constrainAs(submitButton) {
            bottom.linkTo(parent.bottom)
            start.linkTo(g1)
            end.linkTo(g2)
        }, stringResource(R.string.next), height(0.15).dp) { addKitchenViewModel.onPressedNext() }

    }
}






