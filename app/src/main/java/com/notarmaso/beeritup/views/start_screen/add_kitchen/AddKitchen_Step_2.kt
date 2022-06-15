package com.notarmaso.beeritup.views.start_screen.add_kitchen

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

import com.notarmaso.beeritup.ui.theme.components.*

@Composable
fun AddKitchen_Step_2(addKitchenViewModel: AddKitchenViewModel) {
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
        val (kEmail, kPin) = createRefs()
        val (kEmailTxt, kPinTxt) = createRefs()
        val header = createRef()

        val g1 = createGuidelineFromStart(10.dp)
        val g2 = createGuidelineFromEnd(10.dp)

        val txtMargin = 20.dp
        val tfMargin = 20.dp
        val tfHeight = 60.dp

        Text(text = stringResource(R.string.createKitchenHeader), fontSize = 38.sp, textDecoration = TextDecoration.Underline, style =  MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(header){
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, 80.dp)
            })



        SelectionHeader(text = stringResource(R.string.kitchenEmail), true, modifier = Modifier.constrainAs(kEmail){
            top.linkTo(header.bottom, margin = 30.dp)
            centerHorizontallyTo(parent)

        })

        TextFieldName(stringResource(R.string.email_placeholder), Modifier.constrainAs(kEmailTxt){
            centerHorizontallyTo(parent)
            top.linkTo(kEmail.bottom, tfMargin)
        }, addKitchenViewModel,  width(0.66).dp, tfHeight, true)



        SelectionHeader(text = stringResource(R.string.adminPin), true, modifier = Modifier.constrainAs(kPin){
            top.linkTo(kEmailTxt.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPin(stringResource(R.string.minPinChars_placeholer), Modifier.constrainAs(kPinTxt){
            centerHorizontallyTo(parent)
            top.linkTo(kPin.bottom, tfMargin)
        }, addKitchenViewModel, width(0.66).dp, tfHeight)



        SubmitButton(Modifier.constrainAs(submitButton) {
            bottom.linkTo(parent.bottom)
            start.linkTo(g1)
            end.linkTo(g2)
        }, stringResource(R.string.createKithen), height(0.15).dp) { addKitchenViewModel.onConfirm() }

    }
}






