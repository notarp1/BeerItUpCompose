package com.notarmaso.beeritupcompose.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar


@Composable
fun MainMenu(mainMenuViewModel:MainMenuViewModel){
    Column {
        TopBar("Menu", Icons.Rounded.Menu) { }
        MainMenuContents(mainMenuViewModel)
    }

}


@Composable
fun MainMenuContents(viewModel: MainMenuViewModel){
    Box(
        Modifier
            .background(colorResource(id = R.color.background))
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter){
        Column (
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 40.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Text(
                text = "Welcome to BeerItUp!",
                color = colorResource(id = R.color.darkorange),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(50.dp))

            MenuRow(
                buttonText = "Vælg Øl",
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_one),
                goToPage = {
                    viewModel.navigate("selectUser")
                    viewModel.setPage("selectBeer")
                })

            Spacer(modifier = Modifier.height(50.dp))

            MenuRow(
                buttonText = "Tilføj Øl",
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_two),
                goToPage = {
                    viewModel.navigate("selectUser")
                    viewModel.setPage("addBeer")
                })


            Spacer(modifier = Modifier.height(50.dp))

            MenuRow(
                buttonText = "Se betalinger",
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_three),
                goToPage = {
                    viewModel.navigate("selectUser")
                    viewModel.setPage("viewPayments")
                })
        }

    }
}

@Composable
fun MenuRow(buttonText: String, buttonWidth: Double, text: String, goToPage: ()-> Unit){

    Row(verticalAlignment = Alignment.CenterVertically) {
        MenuButton(buttonWidth, buttonText, goToPage)
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = colorResource(id = R.color.darkorange)
        )
    }

}


@Composable
fun MenuButton(widthScale: Double = 1.0, text: String = "knap", goTo: () -> Unit){
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp * widthScale

    OutlinedButton(onClick = { goTo() },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.buttonColor)),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding()
            .width(width.dp)
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.topbarcolor),
            style = MaterialTheme.typography.button
        )

    }

}