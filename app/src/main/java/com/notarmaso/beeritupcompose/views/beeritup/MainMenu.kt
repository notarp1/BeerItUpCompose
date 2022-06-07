package com.notarmaso.beeritupcompose.views.beeritup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.StateHandler
import com.notarmaso.beeritupcompose.ui.theme.LightBlue
import com.notarmaso.beeritupcompose.ui.theme.components.ButtonMain
import com.notarmaso.beeritupcompose.ui.theme.components.TopBar


@Composable
fun MainMenu(mainMenuViewModel: MainMenuViewModel) {
    val vm = mainMenuViewModel
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val isUser by remember { mutableStateOf(mainMenuViewModel.isLoggedInAsUser()) }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.background,
                    MaterialTheme.colors.onPrimary
                )))
    ) {
        val (logoutBtn, loginInfo, topBar, btnAddUser, highscores) = createRefs()

        /*TODO ADD SETTINGS*/
        TopBar(Modifier.constrainAs(topBar) {
            top.linkTo(parent.top)
        }, "menu", goTo = { /*TODO*/ }, Icons.Rounded.Settings)


        LoginInformation(mainMenuViewModel = mainMenuViewModel, isUser, modifier = Modifier.constrainAs(loginInfo) {
            top.linkTo(topBar.bottom, 30.dp)
            centerHorizontallyTo(parent)
        })

        if (!isUser) {
            /*TODO, add onclick*/
            ButtonMain(onClick = { /**/ },
                text = "Add user",
                isInverted = false,
                widthScale = 0.33,
                modifier = Modifier.constrainAs(btnAddUser) {
                    bottom.linkTo(parent.bottom, 20.dp)
                    centerHorizontallyTo(parent, 0.10f)

                })
        }
        ButtonMain(
            onClick = { mainMenuViewModel.logout() },
            text = "Log Out",
            isInverted = false,
            widthScale = 0.33, Modifier.constrainAs(logoutBtn) {
                bottom.linkTo(parent.bottom, 20.dp)
                centerHorizontallyTo(parent, 0.90f)


            }
        )
    }



}

@Composable
private fun LoginInformation(
    mainMenuViewModel: MainMenuViewModel,
    isUser: Boolean,
    modifier: Modifier = Modifier
) {
    val vm = mainMenuViewModel
    when (vm.logInState()) {

        is StateHandler.AppMode.SignedInAsUser -> {
            //nav.navigate(MainActivity.ADD_BEER)
            val user = vm.logInState() as StateHandler.AppMode.SignedInAsUser

            ConstraintLayout(
                modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()) {

                if (user.isAssigned) LoggedIn(user.uName, isUser, vm)
                else NotAssignedToKitchen(user, vm)

            }
        }

        is StateHandler.AppMode.SignedInAsKitchen -> {
            val kitchen = mainMenuViewModel.logInState() as StateHandler.AppMode.SignedInAsKitchen
            ConstraintLayout(
                modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()) {
                LoggedIn(kitchen.kName, isUser, mainMenuViewModel)
            }
        }

        is StateHandler.AppMode.SignedOut -> {

            vm.s.navigate(Pages.START_MENU)
            mainMenuViewModel.logout()

        }

    }


}

@Composable
private fun ConstraintLayoutScope.NotAssignedToKitchen(
    user: StateHandler.AppMode.SignedInAsUser,
    mainMenuViewModel: MainMenuViewModel
) {
    val vm = mainMenuViewModel
    val (uWelcomeTXT, uAssigned, joinBtn) = createRefs()



    Text(text = "Welcome\n${user.uName}", style = MaterialTheme.typography.h1, textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().constrainAs(uWelcomeTXT) {
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)

        })



    Text(text = "You are not assigned to a kitchen!",
        style = MaterialTheme.typography.body1,
        color = colorResource(id = R.color.colorPiiink),
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.constrainAs(uAssigned) {
            centerHorizontallyTo(uWelcomeTXT)
            top.linkTo(uWelcomeTXT.bottom, 20.dp)
        })

    ButtonMain(onClick = { vm.s.navigate(Pages.JOIN_KITCHEN) },
        text = "Join Kitchen",
        isInverted = true,
        widthScale = 0.33,
        modifier = Modifier.constrainAs(joinBtn) {
            centerHorizontallyTo(uAssigned)
            top.linkTo(uAssigned.bottom, 40.dp)


        })
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ConstraintLayoutScope.LoggedIn(name: String, isUser: Boolean, mainMenuViewModel: MainMenuViewModel) {
    val vm = mainMenuViewModel
    val uWelcomeTXT = createRef()
    val (btnDrink, btnAdd, btnLog, btnStats, btnPayments) = createRefs()
    //nav.navigate(MainActivity.STATISTICS)



   Text(text = "Welcome\n$name",
       fontSize = 40.sp,
       style = MaterialTheme.typography.h1,
       textAlign = TextAlign.Center,
       modifier = Modifier.constrainAs(uWelcomeTXT) {
           centerHorizontallyTo(parent)
       })




    ButtonMain(onClick = {
            vm.s.setSelectedPage(Pages.ADD_BEVERAGE_STAGE_1)
            if(isUser) vm.s.navigate(Pages.ADD_BEVERAGE_STAGE_1)
            else vm.s.navigate(Pages.USER_SELECTION)
        },
        text = "Add Beverage",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnAdd) {
            centerHorizontallyTo(parent, 0.20f)
            top.linkTo(uWelcomeTXT.bottom, 30.dp)

        })
    /* TODO STATISTICS FOR KITCHEN */
    ButtonMain(onClick = {
            vm.s.setSelectedPage(Pages.STATISTICS)
            vm.s.navigate(Pages.STATISTICS)
        },
        text = "Statistics",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnStats) {
            centerHorizontallyTo(parent, 0.80f)
            top.linkTo(uWelcomeTXT.bottom, 30.dp)

        })

    ButtonMain(onClick = {
            vm.s.setSelectedPage(Pages.LOGBOOK)
            if(isUser) vm.s.navigate(Pages.LOGBOOK)
            else vm.s.navigate(Pages.USER_SELECTION)
        },
        text = "Logbook",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnLog) {
            centerHorizontallyTo(parent, 0.20f)
            top.linkTo(btnAdd.bottom, 30.dp)

        })

    ButtonMain(onClick = {
            vm.s.setSelectedPage(Pages.PAYMENTS)
            if(isUser) vm.s.navigate(Pages.PAYMENTS)
            else vm.s.navigate(Pages.USER_SELECTION)
        },
        text = "Payments",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnPayments) {
            centerHorizontallyTo(parent, 0.80f)
            top.linkTo(btnAdd.bottom, 30.dp)

        })

    ButtonMain(onClick = {
        vm.s.setSelectedPage(Pages.SELECT_BEVERAGE_STAGE_1)
        if(isUser) vm.s.navigate(Pages.SELECT_BEVERAGE_STAGE_1)
        else vm.s.navigate(Pages.USER_SELECTION)
    },
        text = "Drink Beverage",
        isInverted = false,
        widthScale = 0.75,
        modifier = Modifier.constrainAs(btnDrink) {
            centerHorizontallyTo(parent)
            top.linkTo(btnLog.bottom, 30.dp)

        }, 65.dp, true)
}


@Composable
fun NavigationBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(LightBlue)
    ) {

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()) {
                Icon(
                    Icons.Rounded.Menu,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()) {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()) {
                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }

        }

    }
}