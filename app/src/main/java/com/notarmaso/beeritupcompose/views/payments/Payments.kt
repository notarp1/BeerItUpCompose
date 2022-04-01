package com.notarmaso.beeritupcompose.views.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar


@Preview
@Composable
fun Payments(viewModel: PaymentsViewModel? = null) {

    Column {
        TopBar(title = "Payments", goTo = {viewModel?.navigateBack(MainActivity.SELECT_USER)})
        PaymentsPage(payVm = viewModel)
    }
}

@Composable
fun PaymentsPage(payVm: PaymentsViewModel? = null){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))){

        Column {
            OwedFromList(payments = payVm?.owesTo)
        }
    }
}



@Composable
fun OwedFromList(payments: MutableMap<String, Float>?){

    val names: MutableList<String> = mutableListOf()

    if(payments != null) {
        for ((key, value) in payments.entries) {
            names.add(key)
        }
    }

    val namesToList = remember { names }

    val list: List<String> = namesToList.toList()
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(list) { name ->
                UserPaymentCard(name = name, price = payments?.get(name))
        }
    }

}


@Composable 
fun UserPaymentCard(name: String, price: Float?){
    
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)){
        Text(text = "$name og $price")
    }
    
}

@Composable
fun OwesToList(){

}












