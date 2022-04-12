package com.notarmaso.beeritupcompose.views.debugDrawer

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar

@Composable
fun DebugPinAccess(drawer: DebugDrawerViewModel){
    drawer.pin = ""
    Column {
        TopBar("Menu", Icons.Rounded.ArrowBack) { drawer.navigateBack(Pages.MAIN_MENU) }

        PinPad(drawer = drawer)

    }
}
@Composable
fun PinPad(drawer: DebugDrawerViewModel){
    Box(Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))){

        Column() {
            val orange = colorResource(id = R.color.darkorange)
            val topBarColor = colorResource(id = R.color.topbarcolor)

            Box(
                Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(5.dp), ) {

                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black), contentAlignment = Alignment.CenterEnd){
                    Text(text = drawer.pin, color = orange, fontSize = 60.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(100.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {drawer.pressed("1") },
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "1", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = { drawer.pressed("2")},
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "2", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = { drawer.pressed("3")},
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "3", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { drawer.pressed("4") },
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "4", color = orange,fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = { drawer.pressed("5")},
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "5", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = {drawer.pressed("6") },
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "6", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { drawer.pressed("7") },
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "7", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = {drawer.pressed("8") },
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "8", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Button(onClick = { drawer.pressed("9")},
                    Modifier
                        .height(50.dp)
                        .width(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "9", color = orange, fontSize = 30.sp, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { drawer.onEnter() },
                    Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(start = 20.dp)
                        .padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "ENTER", color = orange)
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { drawer.pin = ""},
                    Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(start = 20.dp)
                        .padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "DEL", color = orange)
                }


            }



        }
    }
}