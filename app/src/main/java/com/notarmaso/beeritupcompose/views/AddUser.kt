package com.notarmaso.beeritupcompose.views

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.User



@Composable
fun AddUser(viewModel: AddUserViewModel) {

    Column {
        TopBar(title = "Add user", goTo = {viewModel.navigateBack(MainActivity.ADD_USER)})
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
            FormRow("Name", "E.G. ModerKornelius_T-46", viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            FormRow("Phone", "E.G. 32532557", viewModel)
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
                MenuButton(text = "Submit", goTo = {viewModel.onSubmit()})

            }
        }
    }
}


@Composable
fun FormRow(text: String = "Name", placeholder: String = "E.G. 32532557", vm: AddUserViewModel){
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
                value = placeholder,
                onValueChange = {if(text == "Name") vm.name = placeholder else vm.phone = placeholder},
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 20.dp)
                    .padding(end = 20.dp),
                singleLine = true)

        }
    }

}

