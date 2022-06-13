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
import com.notarmaso.db_access_setup.ui.theme.components.TextFieldPhone


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
        val (uNameTF, uPhoneTF, uPassTF, uPinTF, uEmailTF) = createRefs()
        val (usernameTxt, phoneTxt, passTxt, uPinTxt, uEmailTxt) = createRefs()
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


        SelectionHeader(text = stringResource(R.string.yourEmail), true, modifier = Modifier.constrainAs(uEmailTxt){
            top.linkTo(uNameTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })

        TextFieldName(stringResource(R.string.email_placeholder), Modifier.constrainAs(uEmailTF){
            centerHorizontallyTo(parent)
            top.linkTo(uEmailTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight, true )

        SelectionHeader(text = stringResource(R.string.yourPhone), true, modifier = Modifier.constrainAs(phoneTxt){
            top.linkTo(uEmailTF.bottom, margin = txtMargin)
            centerHorizontallyTo(parent)

        })


        TextFieldPhone(stringResource(R.string.phone_placeholder), Modifier.constrainAs(uPhoneTF){
            centerHorizontallyTo(parent)
            top.linkTo(phoneTxt.bottom, tfMargin)
        }, addUserViewModel, width(0.66).dp, tfHeight )




        SubmitButton(Modifier.constrainAs(submitButton) {
            bottom.linkTo(parent.bottom)
            start.linkTo(g1)
            end.linkTo(g2)
        }, stringResource(R.string.next), height(0.15).dp) { addUserViewModel.onPressedNext() }

    }

}









