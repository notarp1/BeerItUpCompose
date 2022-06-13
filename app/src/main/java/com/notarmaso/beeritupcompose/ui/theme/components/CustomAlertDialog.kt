package com.notarmaso.beeritupcompose.ui.theme.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

        AlertDialog( modifier = Modifier.width(width(0.75).dp),
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
                    onClick = {
                        onConfirm()
                    }) {
                    Text("Yes, proceed")
                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        onClose()
                    }) {
                    Text("No, cancel")
                }
            }
        )
    }
}
