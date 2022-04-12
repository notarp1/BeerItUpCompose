package com.notarmaso.beeritupcompose.views.logBooks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import org.koin.androidx.compose.get

@Composable
fun LogBook(logBookViewModel: LogBookViewModel){

    val service = get<Service>()
    Column {
        TopBar("${service.currentUser.name}'s Logbook", Icons.Rounded.ArrowLeft) { service.navigateBack(Pages.SELECT_USER) }
        LogBookContents(logBookViewModel)

    }


}


@Composable
fun LogBookContents(vm: LogBookViewModel){

    Box(
        Modifier
            .background(colorResource(id = R.color.background))
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter){
        Column() {


            Column(modifier = Modifier
                .padding(start = 10.dp)
                .padding(end = 10.dp)
                .padding(top = 20.dp), horizontalAlignment = Alignment.Start) {

                Row() {

                    Text(text = "Total spent on bought beers: ",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(
                            id = R.color.topbarcolor))
                    Text(text = "${vm.totalBought} DKK",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(
                            id = R.color.darkorange))
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row() {
                    Text(text = "Total spent on added beers: ", fontWeight = FontWeight.Bold, color = colorResource(
                        id = R.color.topbarcolor))
                    Text(text = "${vm.totalAdded} DKK", fontWeight = FontWeight.Bold, color = colorResource(
                        id = R.color.darkorange))
                }
            }
            Column (
                modifier = Modifier
                    .padding(start = 40.dp)
                    .padding(end = 40.dp)
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Spacer(modifier = Modifier.height(10.dp))

                LogTables(vm)
            }
        }
    }
}


@Composable
fun LogTables(vm: LogBookViewModel){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row() {

            Box(Modifier.fillMaxWidth()) {


                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, Alignment.CenterVertically) {

                    Icon(Icons.Rounded.ArrowLeft,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.decrementMonth() }
                            .size(50.dp),
                        colorResource(
                            id = R.color.darkorange))

                    Text(text = vm.currentPage, fontWeight = FontWeight.Bold, color = colorResource(
                        id = R.color.topbarcolor), textDecoration = TextDecoration.Underline)

                    Icon(Icons.Rounded.ArrowRight,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.incrementMonth() }
                            .size(50.dp),
                        colorResource(
                            id = R.color.darkorange))
                }

            }


        }
        LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

            items(vm.loglist) { log ->
                LogCard(log)
            }
        }
    }

}


@Composable
fun LogCard(log: String){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
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
                Text(text = log, color = colorResource(id = R.color.buttonColor))
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}

