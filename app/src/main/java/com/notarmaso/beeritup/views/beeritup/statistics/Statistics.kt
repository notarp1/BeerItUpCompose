package com.notarmaso.db_access_setup.views.beeritup.statistics

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritup.models.LeaderboardEntryObj
import com.notarmaso.beeritup.ui.theme.components.TopBar


@Composable
fun Statistics(statisticViewModel: StatisticsViewModel){

    val vm = statisticViewModel
    /*TODO: Remake this with observer*/
    vm.reloadHighScores()

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

        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        }, "stats", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack)

        Leaderboard(statisticViewModel, modifier = Modifier.constrainAs(leaderBoard){
            top.linkTo(topBar.bottom, 20.dp)
        })

    }
}


@Composable
fun Leaderboard(vm: StatisticsViewModel, modifier: Modifier = Modifier){
    val leaderboardEntries: List<LeaderboardEntryObj>? = vm.leaderBoard
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Row() {

            Box(Modifier.fillMaxWidth()) {

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, Alignment.CenterVertically) {

                    Icon(Icons.Rounded.ArrowBack,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.decrementMonth() }
                            .size(40.dp))

                    /*Month*/
                    Text(text = vm.currentMonth, style = MaterialTheme.typography.h2, modifier = Modifier.width(200.dp), textAlign = TextAlign.Center )

                    Icon(Icons.Rounded.ArrowForward,
                        contentDescription = "Reload Highscores",
                        modifier = Modifier
                            .clickable { vm.incrementMonth() }
                            .size(40.dp))
                }

            }


        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.padding(top = 5.dp)){
            if(leaderboardEntries != null) {
                var position: Int = 1
                items(leaderboardEntries) { entry ->
                    LeaderboardEntryCard(user = entry, position)
                    position++
                }
            }

        }
    }

}


@Composable
fun LeaderboardEntryCard(user: LeaderboardEntryObj, position: Int){

    Surface(modifier = Modifier
        .width(360.dp)
        .height(45.dp)
        .padding(start = 5.dp)
        .padding(end = 5.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart) {

            Row(
                Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .padding(end = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(text = "$position.", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Normal, modifier = Modifier.width(40.dp), textAlign = TextAlign.Start)
                Text(text = user.name, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Normal, modifier = Modifier.width(220.dp))
                Text(text = "${user.count}", style = MaterialTheme.typography.h4, modifier = Modifier.width(100.dp), textAlign = TextAlign.Center)
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}
