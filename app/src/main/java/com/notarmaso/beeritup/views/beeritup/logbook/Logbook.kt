package com.notarmaso.db_access_setup.views.beeritup.logbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritup.models.BeverageLogEntryObj
import com.notarmaso.beeritup.ui.theme.components.TopBar



@Composable
fun Logbook(vm: LogbookViewModel) {

    /*TODO: Remake this with observer*/
    //vm.reloadHighScores()
    vm.getLogs()

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
        val (leaderBoard, topBar) = createRefs()

        TopBar(Modifier.constrainAs(topBar) {
            top.linkTo(parent.top)
        }, "logbook", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack)

        LogbookList(vm, modifier = Modifier.constrainAs(leaderBoard) {
            top.linkTo(topBar.bottom)
        })

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogbookList(vm: LogbookViewModel, modifier: Modifier = Modifier) {
    val beverageLogEntries: List<BeverageLogEntryObj>? = vm.logs
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Row() {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.background,
                                MaterialTheme.colors.background
                            )
                        )
                    )) {

                Column() {


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Icon(Icons.Rounded.ArrowBack,
                            contentDescription = "Reload Highscores",
                            modifier = Modifier
                                .clickable(enabled = vm.selectedOptionInt != 0) { vm.decrementOption() }
                                .size(40.dp),
                            tint = if (vm.selectedOptionInt == 0) Color.Transparent else MaterialTheme.colors.onPrimary)

                        /*Month*/
                        Text(
                            text = vm.selectedOption,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.width(200.dp),
                            textAlign = TextAlign.Center
                        )

                        Icon(Icons.Rounded.ArrowForward,
                            contentDescription = "Reload Highscores",
                            modifier = Modifier
                                .clickable(enabled = vm.selectedOptionInt != 3) { vm.incrementOption() }
                                .size(40.dp),
                            tint = if (vm.selectedOptionInt == 3) Color.Transparent else MaterialTheme.colors.onPrimary)
                    }
                    Box(Modifier.fillMaxWidth().weight(0.05f).background(MaterialTheme.colors.onPrimary))

                }

            }


        }

        LazyColumn(modifier = Modifier.height((height - 150).dp), contentPadding = PaddingValues(5.dp)) {
            if (beverageLogEntries != null) {

                items(beverageLogEntries) { entry ->
                    LogBeveragesEntry(entry, vm)

                }
            }

        }
    }

}


@Composable
fun LogBeveragesEntry(entry: BeverageLogEntryObj, vm: LogbookViewModel) {

    Surface(
        modifier = Modifier
            .width(500.dp)
            .height(60.dp)
            .padding(start = 5.dp)
            .padding(end = 5.dp)
            .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {

                val dateFromDb = entry.date.toString()
                val date = dateFromDb.substring(0..15)

                when (vm.selectedOptionInt) {


                    0 -> {
                        Column() {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.h6
                                )

                                Text(
                                    text = "${entry.counterpartPhone}",
                                    style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End
                                )
                            }

                            Text(
                                text = "${entry.counterpartName} ${entry.counterpartPhone} paid ${entry.price / 100}kr",
                                style = MaterialTheme.typography.h6, fontWeight = FontWeight.Normal
                            )


                        }

                    }

                    1 -> {
                        Column() {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = date, style = MaterialTheme.typography.h6)

                                Text(
                                    text = "${entry.counterpartPhone}",
                                    style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End
                                )
                            }
                            Text(
                                text = "You paid ${entry.price / 100}kr. to ${entry.counterpartName} ${entry.counterpartPhone}",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Normal
                            )

                        }
                    }

                    2 -> {

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = date, style = MaterialTheme.typography.h6)
                            Text(
                                text = "${entry.count} ${entry.bevName} á ${(entry.price / entry.count) / 100} kr / ${entry.price / 100} kr",
                                style = MaterialTheme.typography.h6, fontWeight = FontWeight.Normal
                            )

                        }

                    }

                    3 -> {

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = date, style = MaterialTheme.typography.h6)
                            Text(
                                text = "${entry.count} ${entry.bevName} á ${(entry.price / entry.count) / 100} kr / ${entry.price / 100} kr",
                                style = MaterialTheme.typography.h6, fontWeight = FontWeight.Normal
                            )

                        }


                    }
                }


            }


        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}

