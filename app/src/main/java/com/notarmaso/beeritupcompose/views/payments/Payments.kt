package com.notarmaso.beeritupcompose.views.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
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
    val configuration = LocalConfiguration.current
    val maxHeight = configuration.screenHeightDp / 2
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background)), contentAlignment = Alignment.TopCenter){

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box(Modifier
                .fillMaxWidth()
                .height(maxHeight.dp - 30.dp)) {
                Text(text = "Money you owe!")
                Spacer(modifier = Modifier.height(10.dp))
                OwedFromList(payments = payVm?.owesTo)
            }
            Box(Modifier
                .fillMaxWidth()
                .height(maxHeight.dp - 30.dp)) {
                Text(text = "Money people owe you!")
                Spacer(modifier = Modifier.height(10.dp))
                OwesToList(payments = payVm?.owedFrom)
            }
           
        }
    }
}


@Composable
fun OwesToList(payments: MutableMap<String, Float>?){

    val names: MutableList<String> = returnListOfNames(payments)

    val namesToList = remember { names }

    val list: List<String> = namesToList.toList()
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(list) { name ->
            UserPaymentCard(name = name, price = payments?.get(name))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun OwedFromList(payments: MutableMap<String, Float>?){

    val names: MutableList<String> = returnListOfNames(payments)

    val namesToList = remember { names }

    val list: List<String> = namesToList.toList()
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(list) { name ->
                UserPaymentCard(name = name, price = payments?.get(name))
        }
    }


}

@Composable
private fun returnListOfNames(payments: MutableMap<String, Float>?): MutableList<String> {
    val names: MutableList<String> = mutableListOf()

    if (payments != null) {
        for ((key, value) in payments.entries) {
            names.add(key)

        }
    }
    return names
}


@Composable 
fun UserPaymentCard(name: String, price: Float?){
    
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(35.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)){
        
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.topbarcolor)), contentAlignment = Alignment.Center) {
            Text(text = "$price DKK $name",
                color = colorResource(id = R.color.darkorange),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 15.dp))
        }
        }
    Spacer(modifier = Modifier.height(10.dp))
    
}













