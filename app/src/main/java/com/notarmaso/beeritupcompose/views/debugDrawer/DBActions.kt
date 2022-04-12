package com.notarmaso.beeritupcompose.views.debugDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.User
import org.koin.androidx.compose.get



@Composable
fun DebugDrawer(drawer: DebugDrawerViewModel){

    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) {
            when {
                drawer.mainMenu -> { drawer.navigateBack(Pages.MAIN_MENU) }
                else -> { drawer.backToMenuPressed() }
            }
        }

        DebugContents(drawer)

    }
}

@Composable
fun DebugContents(drawer: DebugDrawerViewModel){

  Box(Modifier
      .fillMaxSize()
      .background(colorResource(id = R.color.background))) {


        when {
            drawer.mainMenu -> {
                MainRow(drawer)
            }
            drawer.deleteUser -> {
                drawer.getUserList()
                drawer.setCurrentPage(Pages.DELETE_USER.value)
                ChooseUser(drawer = drawer)
            }
            drawer.editUser -> {
                drawer.getUserList()
                drawer.setCurrentPage(Pages.EDIT_USER.value)
                ChooseUser(drawer = drawer)
            }
            drawer.miscellaneous -> {
                drawer.getUserList()
                drawer.setCurrentPage("Misc")
                ChooseUser(drawer = drawer)


            }
        }

    }
}


@Composable
fun ChooseUser(drawer: DebugDrawerViewModel){
    drawer.setSettled()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))){

        LazyColumn (modifier = Modifier.padding(top = 20.dp)) {

            items(drawer.users) { user ->
                UserCardDebug(user, drawer)
            }
        }
    }
}


@Composable
fun UserCardDebug(user: User, drawer: DebugDrawerViewModel){

    val service = get<Service>()
    Surface(
        shape = RoundedCornerShape(50),
        elevation = 1.dp,
        color = colorResource(id = R.color.buttonColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(60.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable {
                    service.currentUser = user
                    when (drawer.currentPage) {
                        Pages.DELETE_USER.value -> {
                            drawer.navigate(Pages.DELETE_USER)
                        }
                        Pages.EDIT_USER.value -> {
                            drawer.navigate(Pages.EDIT_USER)
                        }
                        "Misc" -> {

                        }
                    }
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = user.name,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.topbarcolor),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }


    }
}

@Composable
fun MainRow(drawer: DebugDrawerViewModel){
    val topBarColor = colorResource(id = R.color.topbarcolor)
    val orange = colorResource(id = R.color.darkorange)

    Column() {


        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .padding(end = 10.dp)) {

            Button(onClick = { drawer.deleteUserPressed() },
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Delete User", color = orange)
            }

            Button(onClick = { drawer.editUserPressed() },
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Edit User", color = orange)
            }

            Button(onClick = { drawer.miscPressed() },
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Misc.", color = orange)
            }
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .padding(end = 10.dp)) {


            Button(onClick = { /*drawer.resetUsers()*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Reset Everything", color = orange)
            }
        }
    }
}
@Composable
fun DeleteUser(drawer: DebugDrawerViewModel){
    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) { drawer.navigateBack(Pages.DEBUG_DRAWER) }
        DeleteUserPage(drawer = drawer)
    }
}

@Composable
fun DeleteUserPage(drawer: DebugDrawerViewModel){
    val orange = colorResource(id = R.color.darkorange)
    val service = get<Service>()

    Box(modifier = Modifier
        .background(colorResource(id = R.color.background))
        .fillMaxSize()) {
        Column{
            
            Text(text = "Name: ${service.currentUser.name}",
                fontWeight = FontWeight.Bold,
                color = orange)
            
            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)) {

                Button(
                    onClick = { drawer.deleteUser(service.currentUser) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.topbarcolor))
                ) {
                    Text(text = "Delete User", fontWeight = FontWeight.Bold, color = orange)
                }


            }

            Spacer(modifier = Modifier.height(10.dp))
            
            if(drawer.notSettled){
                Text(text = "There are unsettled payments!!!\nPlease settle all payments from/to before deleting account!", color = orange, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            }
        }
    }
}


@Composable
fun EditUser(drawer: DebugDrawerViewModel){
    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) { drawer.navigateBack(Pages.DEBUG_DRAWER) }
        EditUserPage(drawer = drawer)
    }
}



@Composable
fun EditUserPage(drawer: DebugDrawerViewModel){
    val topBarColor = colorResource(id = R.color.topbarcolor)
    val orange = colorResource(id = R.color.darkorange)
    val service = get<Service>()



    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
            .padding(end = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Edit user: ${service.currentUser.name}")


            Spacer(modifier = Modifier.height(20.dp))

            TextField(value = drawer.newPhone,
                onValueChange = { newText -> drawer.newPhone = newText }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Button(onClick = { drawer.editPhone() },
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Edit Phone", color = orange)
            }
           CustomSpacer()


            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Beer count")
                DropDownMenuMonths(drawer = drawer)
                TextField(value = drawer.newCount,
                    onValueChange = { txt -> drawer.newCount = txt} ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(60.dp))
            }

            Button(onClick = { drawer.editTotalBeers()},
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Edit Total Beers.", color = orange)
            }

            CustomSpacer()


            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                DropDownMenu(drawer = drawer)
                TextField(value = drawer.newPrice,
                    onValueChange = { txt -> drawer.newPrice = txt} ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(120.dp))
            }


            Button(onClick = {drawer.editWhoOwedFrom()},
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Edit: How much ${drawer.selectedUserName} owes ${service.currentUser.name}", color = orange)
            }


            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { drawer.editWhoOwesTo()},
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                Text(text = "Edit: How much ${service.currentUser.name} owes ${drawer.selectedUserName}", color = orange)
            }
            CustomSpacer()
        }

    }

}

@Composable
fun CustomSpacer(){
    Spacer(modifier = Modifier.height(19.dp))
    Box(Modifier
        .fillMaxWidth()
        .height(2.dp)
        .background(colorResource(id = R.color.topbarcolor)))
    Spacer(modifier = Modifier.height(19.dp))
}

@Composable
fun DropDownMenu(drawer: DebugDrawerViewModel){
    var expanded by remember { mutableStateOf(false) }
    val items = drawer.users
    var selectedIndex by remember { mutableStateOf(0) }
    drawer.selectedUser = items[selectedIndex]
    Surface(modifier = Modifier.width(200.dp), shape = RoundedCornerShape(20.dp)) {


    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopCenter)) {

        Text( "  ${drawer.selectedUser.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .clickable(onClick = { expanded = true })
                .background(colorResource(id = R.color.topbarcolor)), color = colorResource(id = R.color.darkorange), fontSize = 24.sp)

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.topbarcolor))
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    drawer.selectedUser = items[selectedIndex]
                    drawer.selectedUserName = items[selectedIndex].name
                    expanded = false
                }) {
                    Text(text = s.name, color = colorResource(id = R.color.darkorange))
                }
            }
        }
    }
    }
}


@Composable
fun DropDownMenuMonths(drawer: DebugDrawerViewModel){
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("JANUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
    var selectedIndex by remember { mutableStateOf(0) }
    drawer.selectMonth = items[selectedIndex]

    Surface(modifier = Modifier.width(100.dp), shape = RoundedCornerShape(20.dp)) {


        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter), contentAlignment = Alignment.Center) {

            Text(   drawer.selectMonth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .clickable(onClick = { expanded = true })
                    .background(colorResource(id = R.color.topbarcolor)), color = colorResource(id = R.color.darkorange), fontSize = 18.sp)

                DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.topbarcolor))
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        drawer.selectMonth = items[selectedIndex]
                        expanded = false
                    }, contentPadding = PaddingValues(top=20.dp)) {
                        Text(text = s, color = colorResource(id = R.color.darkorange), )
                    }
                }
            }
        }
    }
}












