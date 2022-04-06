package com.notarmaso.beeritupcompose.views.mainMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.DonutLarge
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.BeerService
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.UserLeaderboard
import org.koin.androidx.compose.get


@Composable
fun MainMenu(mainMenuViewModel: MainMenuViewModel){

    val service = get<Service>()
    Column {
        TopBar("Menu", Icons.Rounded.DonutLarge) { /*service.navigateBack(MainActivity.DEBUG_DRAWER)*/ }
        MainMenuContents(mainMenuViewModel)

    }


}


@Composable
fun MainMenuContents(viewModel: MainMenuViewModel){
    val service = get<Service>()
    val beerService = get<BeerService>()
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
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Text(
                text = stringResource(R.string.welcome),
                color = colorResource(id = R.color.darkorange),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            MenuRow(
                buttonText = stringResource(R.string.drink_beer),
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_one),
                goToPage = {
                    viewModel.setPage(MainActivity.BUY_BEER)
                    service.observer.notifySubscribers(MainActivity.SELECT_USER)
                    viewModel.navigate(MainActivity.SELECT_USER)
                })

            Spacer(modifier = Modifier.height(15.dp))

            MenuRow(
                buttonText = stringResource(R.string.see_payments),
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_three),
                goToPage = {
                    viewModel.setPage(MainActivity.PAYMENTS)
                    service.observer.notifySubscribers(MainActivity.SELECT_USER)
                    viewModel.navigate(MainActivity.SELECT_USER)
                })

            Spacer(modifier = Modifier.height(15.dp))

            MenuRow(
                buttonText = stringResource(R.string.add_beers),
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_two),
                goToPage = {
                    viewModel.setPage(MainActivity.ADD_BEER)
                    service.observer.notifySubscribers(MainActivity.SELECT_USER)
                    service.observer.notifySubscribers(MainActivity.ADD_BEER)
                    viewModel.navigate(MainActivity.SELECT_USER)
                })



            Spacer(modifier = Modifier.height(15.dp))

            MenuRow(
                buttonText = stringResource(R.string.create_user),
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_four),
                goToPage = {
                    viewModel.setPage(MainActivity.ADD_USER)
                    viewModel.navigate(MainActivity.ADD_USER)
                })
            Spacer(modifier = Modifier.height(15.dp))

            MenuRow(
                buttonText = stringResource(R.string.log_books),
                buttonWidth = 0.35,
                text = stringResource(id = R.string.menu_btn_five),
                goToPage = {
                    viewModel.setPage(MainActivity.LOG_BOOKS)
                    service.observer.notifySubscribers(MainActivity.SELECT_USER)
                    viewModel.navigate(MainActivity.SELECT_USER)
                })
            Spacer(modifier = Modifier.height(10.dp))

            Highscores(viewModel)
        }

    }
}

@Composable
fun Highscores(vm: MainMenuViewModel){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row() {

            Box(Modifier.fillMaxWidth()) {



                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, Alignment.CenterVertically) {

                    Icon(Icons.Rounded.ArrowLeft,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.decrementMonth() }
                            .size(50.dp),
                    colorResource(id = R.color.topbarcolor))

                    Text(text = vm.currentMonth, color = colorResource(id = R.color.topbarcolor), fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)

                    Icon(Icons.Rounded.ArrowRight,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.incrementMonth() }
                            .size(50.dp),
                        colorResource(id = R.color.topbarcolor))
                }

            }


        }
        LazyColumn(modifier = Modifier.padding(top = 5.dp)) {

            items(vm.userList) { users ->
                HighScoreCard(users)
            }
        }
    }

}


@Composable
fun HighScoreCard(user: UserLeaderboard){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(30.dp)
        .padding(start = 5.dp)
        .padding(end = 5.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.topbarcolor)),
            contentAlignment = Alignment.CenterStart) {

            Row(Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
                .padding(end = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(text = "${user.count} beers", color = colorResource(id = R.color.buttonColor), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = user.name, color = colorResource(id = R.color.darkorange))
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}


@Composable
fun MenuRow(buttonText: String, buttonWidth: Double, text: String, goToPage: ()-> Unit){

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        MenuButton(buttonWidth, buttonText, goToPage)
        Spacer(modifier = Modifier.width(25.dp))
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