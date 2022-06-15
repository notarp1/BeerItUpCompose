package com.notarmaso.beeritup.ui.theme.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



@Composable
fun CustomAlertDialog(isOpened: Boolean = false, onClose: () -> Unit, onConfirm: () -> Unit, title: String, text: String, onOpened: ()->Unit = {}) {
    val configuration = LocalConfiguration.current
    fun height(heightScale: Double): Double { return configuration.screenHeightDp * heightScale }
    fun width(widthScale: Double): Double { return configuration.screenWidthDp * widthScale }
    onOpened()

    if (isOpened) {

        AlertDialog( modifier = Modifier.width(width(0.85).dp), shape = RoundedCornerShape(20.dp),
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                onClose()
            },

            title = {
                Text(title, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.primary)
            },
            text = {
                Text(text, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Normal, color = MaterialTheme.colors.primary)
            },
            confirmButton = {
                Button(
                    shape = RoundedCornerShape(40.dp),
                    onClick = {
                        onConfirm()
                    }) {
                    Text("Yes, proceed")
                }
            },
            dismissButton = {
                Button(
                    shape = RoundedCornerShape(40.dp),
                    onClick = {
                        onClose()
                    }) {
                    Text("No, cancel")
                }
            }
        )
    }
}

@Composable
fun LoadingIndicator(isOpened: Boolean = false) {


    if (isOpened) {

        AlertDialog( modifier = Modifier.width(140.dp), shape = RoundedCornerShape(20.dp),
            onDismissRequest = {
            },

            title = {
                Text("Loading", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.primary)
            },
            text = {
              CircularProgressIndicator()
            },
            confirmButton = {

            },
            dismissButton = {

            }
        )
    }
}

