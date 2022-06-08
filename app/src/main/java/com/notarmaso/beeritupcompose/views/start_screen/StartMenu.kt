package com.notarmaso.beeritupcompose.views.start_screen

import android.app.Service
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.ui.theme.components.ButtonMain
import com.notarmaso.beeritupcompose.ui.theme.components.SelectionHeader

@Composable
fun StartMenu(startMenuViewModel: StartMenuViewModel) {
    val vm = startMenuViewModel

    val configuration = LocalConfiguration.current
    fun width(widthScale: Double): Double {
        return configuration.screenWidthDp * widthScale
    }
    fun height(widthScale: Double): Double {
        return configuration.screenHeightDp * widthScale
    }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.background,
                    MaterialTheme.colors.onPrimary
                ))) ) {
        // Create references for the composables to constrain
        val (button, text) = createRefs()
        val divider = createRef()
        val titleBox = createRef()
        val (kSignBtn, uSignBtn, kLogBtn, uLogBtn) = createRefs()
        val (signUpTxt, logInTxt, orSignUp, orSignIn) = createRefs()
        val (version, header) = createRefs()

        val g1 = createGuidelineFromStart(10.dp)
        val g2 = createGuidelineFromEnd(10.dp)


        Text(text = "Login & Signup", fontSize = 34.sp,
            textDecoration = TextDecoration.Underline, style =  MaterialTheme.typography.h1, modifier = Modifier.constrainAs(header){
            centerHorizontallyTo(parent)
            top.linkTo(parent.top, 40.dp)
        })
       /* HEADER */

        HeaderBox(headerTxt = "Welcome to BeerItUp!", Modifier.constrainAs(titleBox) {
            bottom.linkTo(parent.bottom)
            start.linkTo(g1)
            end.linkTo(g2)
        }, height(0.15).dp)

        Text(text = "BeerItUp v. 1.4", fontSize = 10.sp, color = MaterialTheme.colors.primary, style =  MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(version){
                end.linkTo(g2)
                bottom.linkTo(titleBox.top, 5.dp)

        })



        ButtonMain(
            onClick = {  vm.s.navigate(Pages.LOGIN_USER) },
            text = "User",
            isInverted = false,
            widthScale = 0.33,
            modifier = Modifier.constrainAs(uLogBtn){
                bottom.linkTo(logInTxt.top, margin = 30.dp)
                end.linkTo(orSignIn.start, margin = 20.dp)}
        )


        ButtonMain(
            onClick = {   vm.s.navigate(Pages.LOGIN_KITCHEN)},
            text = "Kitchen",
            isInverted = false,
            widthScale = 0.33,
            modifier = Modifier.constrainAs(kLogBtn){
                bottom.linkTo(logInTxt.top, margin = 30.dp)
                start.linkTo(orSignIn.end, margin = 20.dp)}
        )



        SelectionHeader(text = "Or..", modifier = Modifier.constrainAs(orSignIn){
            bottom.linkTo(logInTxt.top, margin = 40.dp)
            centerHorizontallyTo(parent)
        })


        SelectionHeader(text = "I want to SIGN IN as", true, modifier = Modifier.constrainAs(logInTxt){
            bottom.linkTo(divider.top, margin = 40.dp)
            centerHorizontallyTo(parent)
            end.linkTo(g2)
        })


        /* DIVIDER */

        Divider(modifier = Modifier.constrainAs(divider) { centerVerticallyTo(parent, 0.45f) })




        SelectionHeader(text = "I want to SIGN UP as", true, modifier = Modifier.constrainAs(signUpTxt){
            top.linkTo(divider.bottom, margin = 40.dp)
            centerHorizontallyTo(parent)
            end.linkTo(g2)
        })

        SelectionHeader(text = "Or..", modifier = Modifier.constrainAs(orSignUp){
            top.linkTo(signUpTxt.bottom, margin = 40.dp)
            centerHorizontallyTo(parent)
        })

        ButtonMain(
            onClick = {  vm.s.navigate(Pages.ADD_KITCHEN) },
            text = "Kitchen",
            isInverted = false,
            widthScale = 0.33,
            modifier = Modifier.constrainAs(uSignBtn){
                top.linkTo(signUpTxt.bottom, margin = 30.dp)
                end.linkTo(orSignUp.start, margin = 20.dp)}
        )



        ButtonMain(
            onClick = {  vm.s.navigate(Pages.ADD_USER)},
            text = "User",
            isInverted = false,
            widthScale = 0.33,
            modifier = Modifier.constrainAs(kSignBtn){
                top.linkTo(signUpTxt.bottom, margin = 30.dp)
                start.linkTo(orSignUp.end, margin = 20.dp)

            }
        )


    }



}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(MaterialTheme.colors.onBackground)

    )
}

@Composable
fun HeaderBox(headerTxt: String, modifier: Modifier = Modifier, height: Dp) {
    Box(
        modifier
            .background(
                MaterialTheme.colors.primary
            )
            .fillMaxWidth()
            .height(height)
            .padding(15.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = headerTxt,
            style = MaterialTheme.typography.h2
        )
    }
}









