package com.notarmaso.beeritupcompose.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.R

/*TODO COLORS?*/

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "TopBar Text",
    goTo: () -> Unit,
    icon: ImageVector = Icons.Rounded.ArrowBack, ){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(colorResource(id = R.color.colorBlackGrey))
    ){
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { goTo() }) {
                Icon(icon, contentDescription = "Localized description",
                    Modifier
                        .fillMaxHeight()
                        .width(40.dp))
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = colorResource(id = R.color.colorLightGreen)
            )
        }

    }

}

@Composable
fun SubmitButton(modifier: Modifier = Modifier, txt: String = "Button", height: Dp = 20.dp, onClick: () -> Unit = { println("Not implemented") }) {

    Button(onClick = { onClick()},
        colors= ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorBlackGrey)),
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(0),
        ) {
        Text(
            text = txt,
            style = MaterialTheme.typography.h1
        )
    }


}


@Composable
fun ButtonMain(onClick: () -> Unit, text: String, isInverted: Boolean, widthScale: Double, modifier: Modifier = Modifier, height: Dp = 50.dp, biggerFont: Boolean = false) {

    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp * widthScale



   Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(50), colors = if(isInverted) ButtonDefaults.buttonColors(MaterialTheme.colors.onPrimary) else  ButtonDefaults.buttonColors(MaterialTheme.colors.primary) ,
        border = BorderStroke(
           2.dp,
            if(isInverted) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
        ),
        modifier = modifier
            .width(width.dp)
            .height(height)
    ) {
        Text(
            text = text,
            fontSize =  if(biggerFont) 18.sp else 14.sp,
            color = if(isInverted) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
        )
    }
}



@Composable
fun ButtonSelection(onClick: () -> Unit, widthScale: Double, isInc: Boolean = false) {

    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp * widthScale



    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            2.dp,
            MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier
            .width(width.dp)
            .height(50.dp)
            .clickable { },
    ) {

        Icon(if(!isInc) Icons.Sharp.KeyboardArrowDown else Icons.Sharp.KeyboardArrowUp,
            contentDescription = "Reload Highscores")
    }
}


@Composable
fun SelectionHeader(text: String, underline: Boolean = false, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = if (underline) MaterialTheme.typography.h2 else MaterialTheme.typography.h4,
        fontStyle = FontStyle.Italic
    )
}
