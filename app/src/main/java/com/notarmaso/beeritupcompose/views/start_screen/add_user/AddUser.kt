package com.notarmaso.db_access_setup.views.start_screen.add_user
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.ui.theme.components.SelectionHeader
import com.notarmaso.beeritupcompose.ui.theme.components.SubmitButton
import com.notarmaso.beeritupcompose.views.start_screen.add_user.AddUserViewModel
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldName
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPassword
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPhone
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPin


@Composable
fun AddUser(addUserViewModel: AddUserViewModel) {
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
        val (uNameTF, uPhoneTF, uPassTF, uPinTF) = createRefs()
        val (usernameTxt, phoneTxt, passTxt, uPinTxt) = createRefs()
        val header = createRef()

        val g1 = createGuidelineFromStart(10.dp)
        val g2 = createGuidelineFromEnd(10.dp)
        val txtMargin = 20.dp
        val tfMargin = 5.dp
        val tfHeight = 60.dp

        Text(text = stringResource(R.string.createUserTitle),
            fontSize = 34.sp, textDecoration = TextDecoration.Underline, style =  MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(header){
            centerHorizontallyTo(parent)
            top.linkTo(parent.top, 40.dp)
        })


        SelectionHeader(text = stringResource(R.string.username), true, modifier = Modifier.constrainAs(usernameTxt){
            top.linkTo(header.bottom, margin = 30.dp)
            centerHorizontallyTo(parent)

        })

        TextFieldName(stringResource(R.string.name_placeholder), Modifier.constrainAs(uNameTF){
            centerHorizontallyTo(parent)
            top.linkTo(usernameTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight)


        SelectionHeader(text = stringResource(R.string.yourPhone), true, modifier = Modifier.constrainAs(phoneTxt){
            top.linkTo(uNameTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPhone(stringResource(R.string.phone_placerholder), Modifier.constrainAs(uPhoneTF){
            centerHorizontallyTo(parent)
            top.linkTo(phoneTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight )

        SelectionHeader(text = stringResource(R.string.c8UserPassword), true, modifier = Modifier.constrainAs(passTxt){
            top.linkTo(uPhoneTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPassword(stringResource(R.string.min6chars_placeholder), Modifier.constrainAs(uPassTF){
            centerHorizontallyTo(parent)
            top.linkTo(passTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight)


        SelectionHeader(text = stringResource(R.string.youPin), true, modifier = Modifier.constrainAs(uPinTxt){
            top.linkTo(uPassTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldPin(stringResource(R.string.minPinChars_placeholer), Modifier.constrainAs(uPinTF){
            centerHorizontallyTo(parent)
            top.linkTo(uPinTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight)




        SubmitButton(Modifier.constrainAs(submitButton) {
            bottom.linkTo(parent.bottom)
            start.linkTo(g1)
            end.linkTo(g2)
        }, stringResource(R.string.createUser), height(0.15).dp) { addUserViewModel.createUser(1) }

    }

}








