package com.notarmaso.beeritupcompose.views.beeritup.select_user

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.models.UserRecieve
import com.notarmaso.beeritupcompose.ui.theme.components.TopBar


@Composable
fun SelectUser(selectUserViewModel: SelectUserViewModel) {
    val vm = selectUserViewModel


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
        val (userList, topBar) = createRefs()

        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        },"select user", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack,)


        UserList(selectUserViewModel, modifier = Modifier.constrainAs(userList){
            top.linkTo(topBar.bottom)
        })

    }



}


@Composable
fun UserList(vm: SelectUserViewModel, modifier: Modifier = Modifier){

    val users: List<UserRecieve>? = vm.userList
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    Box(modifier = modifier
        .background(Color.Transparent)
        .height((height-70).dp)){

        LazyColumn(contentPadding = PaddingValues(5.dp, 10.dp, 5.dp,10.dp)){
            if(users != null) {
                items(users) { user ->
                    UserCard(user = user, vm)
                }
            }
        }

    }
}

@Composable
fun UserCard(user: UserRecieve, vm: SelectUserViewModel){

    Surface(
        shape = RoundedCornerShape(50),
        elevation = 1.dp,
        border = BorderStroke(2.dp, color = colorResource(id = R.color.colorLightGreen)),
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(50.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .border(
                    BorderStroke(2.dp, color = colorResource(id = R.color.colorLightGreen)))
                .clickable { vm.navigateToPage(user)},
            contentAlignment = Alignment.CenterStart
        ) {
            Row() {
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = user.name,
                    style = MaterialTheme.typography.h1
                )
            }

        }


    }
}