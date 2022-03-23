package com.notarmaso.beeritupcompose.views.addUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.views.mainMenu.MenuButton


@Composable
fun AddUser(viewModel: AddUserViewModel) {

    Column {
        TopBar(title = "Add user", goTo = {viewModel.navigateBack(MainActivity.MAIN_MENU)})
        UserForm(viewModel)
    }
}

@Composable
fun UserForm(viewModel: AddUserViewModel){
    Box(modifier = Modifier
        .background(colorResource(id = R.color.background))
        .fillMaxSize(),
    contentAlignment = Alignment.TopCenter){

        Column(Modifier.padding(40.dp)) {
            FormRow("Name", "KennedKnappeRød_T-44", viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            FormRow("Phone", "Eg. 23543245", viewModel)
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
                MenuButton(text = "Submit", goTo = {viewModel.onSubmit()})

            }
        }
    }
}


@Composable
fun FormRow(text: String = "Name", placerholder: String = "placeholder", vm: AddUserViewModel){
    var input by remember { mutableStateOf("") }

    Surface(shape = RoundedCornerShape(40.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.DarkGray)) {
            Text(
                text = text,
                color = colorResource(id = R.color.darkorange),
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = input,
                onValueChange = { newText -> input = newText
                                if(text == "Name") vm.name = input else vm.phone = input},
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 20.dp)
                    .padding(end = 20.dp),
                textStyle = TextStyle(Color.White),
            singleLine = true,
            placeholder = {Text(placerholder, color = Color.White)})

        }
    }

}

