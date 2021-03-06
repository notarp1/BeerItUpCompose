package com.notarmaso.beeritup.views.beeritup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.R
import com.notarmaso.beeritup.StateHandler
import com.notarmaso.beeritup.ui.theme.components.CustomAlertDialog
import com.notarmaso.beeritup.ui.theme.components.ButtonMain
import com.notarmaso.beeritup.ui.theme.components.TopBar


@Composable
fun MainMenu(mainMenuViewModel: MainMenuViewModel) {


    val vm = mainMenuViewModel

    val loggedInAsKitchen = vm.loggedInAsKitchen

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.onPrimary
                    )
                )
            )
    ) {
        val (logoutBtn, loginInfo, topBar, btnAddUser) = createRefs()

        /*TODO ADD SETTINGS*/
        TopBar(Modifier.constrainAs(topBar) {
            top.linkTo(parent.top)
        }, "menu", goTo = { /*TODO*/ }, Icons.Rounded.Settings)


        LoginInformation(
            mainMenuViewModel = vm,
            modifier = Modifier.constrainAs(loginInfo) {
                top.linkTo(topBar.bottom, 30.dp)
                centerHorizontallyTo(parent)
            })

        if (loggedInAsKitchen) {

            ButtonMain(onClick = { vm.navigate(Pages.ADD_USER) },
                text = "Add user",
                isInverted = false,
                widthScale = 0.33,
                modifier = Modifier.constrainAs(btnAddUser) {
                    bottom.linkTo(parent.bottom, 20.dp)
                    centerHorizontallyTo(parent, 0.10f)

                })
        }

        LogOutButton(logoutBtn) { vm.logout() }
    }

}

@Composable
private fun ConstraintLayoutScope.LogOutButton(
    logoutBtn: ConstrainedLayoutReference,
    onConfirm: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    CustomAlertDialog(
        isOpened = openDialog,
        onClose = { openDialog = !openDialog },
        onConfirm = { onConfirm() },
        title = "You are logging out!",
        text ="Do you want to proceed?"
    )

    ButtonMain(
        onClick = { openDialog = true },
        text = "Log Out",
        isInverted = false,
        widthScale = 0.33, Modifier.Companion.constrainAs(logoutBtn) {
            bottom.linkTo(parent.bottom, 20.dp)
            centerHorizontallyTo(parent, 0.90f)


        }
    )
}


@Composable
private fun LoginInformation(
    mainMenuViewModel: MainMenuViewModel,
    modifier: Modifier = Modifier
) {
    when (mainMenuViewModel.logInState()) {
        is StateHandler.AppMode.SignedInAsUser -> {
            val user = mainMenuViewModel.logInState() as StateHandler.AppMode.SignedInAsUser

            ConstraintLayout(
                modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            ) {

                if (user.isAssigned) LoggedIn(user.uName, false, mainMenuViewModel)
                else NotAssignedToKitchen(user, mainMenuViewModel)

            }
        }

        is StateHandler.AppMode.SignedInAsKitchen -> {
            val kitchen = mainMenuViewModel.logInState() as StateHandler.AppMode.SignedInAsKitchen
            ConstraintLayout(
                modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            ) {
                LoggedIn(kitchen.kName, true, mainMenuViewModel)
            }
        }

        is StateHandler.AppMode.SignedOut -> {
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



    Text(text = "Welcome\n${user.uName}",
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .constrainAs(uWelcomeTXT) {
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

    ButtonMain(onClick = { vm.navigate(Pages.JOIN_KITCHEN) },
        text = "Join Kitchen",
        isInverted = false,
        widthScale = 0.33,
        modifier = Modifier.constrainAs(joinBtn) {
            centerHorizontallyTo(uAssigned)
            top.linkTo(uAssigned.bottom, 40.dp)


        })
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ConstraintLayoutScope.LoggedIn(
    name: String,
    isKitchen: Boolean,
    mainMenuViewModel: MainMenuViewModel
) {
    val vm = mainMenuViewModel
    val uWelcomeTXT = createRef()
    val (btnDrink, btnAdd, btnLog, btnStats, btnPayments) = createRefs()
    //nav.navigate(MainActivity.STATISTICS)


    Text(text = "Welcome\n$name",
        fontSize = 50.sp,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier.constrainAs(uWelcomeTXT) {
            centerHorizontallyTo(parent)
        })




    ButtonMain(onClick = {
        vm.s.setCurrentPage(Pages.ADD_BEVERAGE_STAGE_1)
        if (!isKitchen) vm.navigate(Pages.ADD_BEVERAGE_STAGE_1)
        else vm.navigate(Pages.USER_SELECTION)
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
        vm.s.setCurrentPage(Pages.STATISTICS)
        vm.navigate(Pages.STATISTICS)
    },
        text = "Statistics",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnStats) {
            centerHorizontallyTo(parent, 0.80f)
            top.linkTo(uWelcomeTXT.bottom, 30.dp)

        })

    ButtonMain(onClick = {
        vm.s.setCurrentPage(Pages.LOGBOOKS)
        if (!isKitchen) vm.navigate(Pages.LOGBOOKS)
        else vm.navigate(Pages.USER_SELECTION)
    },
        text = "Logbook",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnLog) {
            centerHorizontallyTo(parent, 0.20f)
            top.linkTo(btnAdd.bottom, 30.dp)

        })

    ButtonMain(onClick = {
        vm.s.setCurrentPage(Pages.PAYMENTS)
        if (!isKitchen) vm.navigate(Pages.PAYMENTS)
        else vm.navigate(Pages.USER_SELECTION)
    },
        text = "Payments",
        isInverted = false,
        widthScale = 0.35,
        modifier = Modifier.constrainAs(btnPayments) {
            centerHorizontallyTo(parent, 0.80f)
            top.linkTo(btnAdd.bottom, 30.dp)

        })

    ButtonMain(
        onClick = {
            vm.s.setCurrentPage(Pages.SELECT_BEVERAGE_STAGE_1)
            if (!isKitchen) vm.navigate(Pages.SELECT_BEVERAGE_STAGE_1)
            else vm.navigate(Pages.USER_SELECTION)
        },
        text = "Drink Beverage",
        isInverted = false,
        widthScale = 0.75,
        modifier = Modifier.constrainAs(btnDrink) {
            centerHorizontallyTo(parent)
            top.linkTo(btnLog.bottom, 30.dp)

        }, 65.dp, true
    )
}


