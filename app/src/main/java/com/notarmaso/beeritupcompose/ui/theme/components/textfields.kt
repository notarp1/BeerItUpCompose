package com.notarmaso.db_access_setup.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.interfaces.Form

@Composable
fun TextFieldName(placeholder: String, modifier: Modifier = Modifier, vm: Form, width: Dp, height: Dp = 60.dp) {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,

        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal, color=MaterialTheme.colors.primary, textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()) },

        onValueChange = {
                newText -> input = newText
                vm.setName(input) },

        shape = RoundedCornerShape(20.dp),
        modifier = modifier.width(width).height(height),

        textStyle = TextStyle(color = MaterialTheme.colors.primary, textAlign = TextAlign.Center, fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.secondary),
        singleLine = true
    )
}

@Composable
fun TextFieldPhone(placeholder: String, modifier: Modifier = Modifier, vm: Form, width: Dp, height: Dp = 60.dp) {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,

        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal, color=MaterialTheme.colors.primary,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },

        onValueChange = {
                newText -> input = newText
                vm.setPhone(input) },

        shape = RoundedCornerShape(20.dp),
        modifier = modifier.width(width).height(height),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        textStyle = TextStyle(color = MaterialTheme.colors.primary, textAlign = TextAlign.Center, fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.secondary),
        singleLine = true
    )
}

@Composable
fun TextFieldPassword(placeholder: String, modifier: Modifier = Modifier, vm: Form, width: Dp, height: Dp = 60.dp) {
    var input by remember { mutableStateOf("") }
    OutlinedTextField(
        value = input,

        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal, color=MaterialTheme.colors.primary,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },

        onValueChange = {
                newText -> input = newText
                vm.setPass(input) },

        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier.width(width).height(height),

        textStyle = TextStyle(color = MaterialTheme.colors.primary, textAlign = TextAlign.Center, fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.secondary),
        singleLine = true
    )
}

@Composable
fun TextFieldPin(placeholder: String, modifier: Modifier = Modifier, vm: Form, width: Dp, height: Dp = 60.dp) {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,

        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal, color=MaterialTheme.colors.primary,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },

        onValueChange = { newText -> input = newText
            vm.setPin(input) },

        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.width(width).height(height),

        textStyle = TextStyle(color = MaterialTheme.colors.primary, textAlign = TextAlign.Center, fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.secondary),
        singleLine = true
    )
}

