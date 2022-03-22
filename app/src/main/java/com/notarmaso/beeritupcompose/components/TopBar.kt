package com.notarmaso.beeritupcompose.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.R


@Composable
fun TopBar(title: String = "TopBar Text", icon: ImageVector = Icons.Rounded.ArrowBack, goTo: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(colorResource(id = R.color.topbarcolor))
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
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.darkorange)
            )
        }
       
    }

}