package com.notarmaso.beeritupcompose.views.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.UserEntry
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState



@Composable
fun Payments(viewModel: PaymentsViewModel) {

    Column {
        TopBar(title = "Payments", goTo = {viewModel.navigateBack(MainActivity.SELECT_USER)})

        PaymentsPage(payVm = viewModel)

    }
}

@Composable
fun PaymentsPage(payVm: PaymentsViewModel){
    val configuration = LocalConfiguration.current
    val maxHeight = configuration.screenHeightDp / 2

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))){

        Column{

            Spacer(modifier = Modifier.height(5.dp))
            Box(Modifier.fillMaxWidth().height(maxHeight.dp - 25.dp), contentAlignment = Alignment.TopCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Money you owe!", textAlign = TextAlign.Center, color = colorResource(id = R.color.darkorange), textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    OwesToList(payVm)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(Modifier.fillMaxWidth().height(maxHeight.dp - 20.dp), contentAlignment = Alignment.TopCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Money people owe you!", textAlign = TextAlign.Center, color = colorResource(id = R.color.darkorange), textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    OwedFromList(payVm)
                }
            }
        }
    }
}


@Composable
fun OwesToList(payVm: PaymentsViewModel){

    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
        items(payVm.owesTo) { user ->
            if(user.price > 0f) UserPaymentCard(user, payVm = payVm, true)
        }
    }

}

@Composable
fun OwedFromList(payVm: PaymentsViewModel){

    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(payVm.owedFrom) { user ->
            if(user.price > 0f) UserPaymentCard(user, payVm)
        }
    }

}




@Composable 
fun UserPaymentCard(user: UserEntry ,payVm: PaymentsViewModel, isClickable: Boolean = false){
    
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .padding(start = 10.dp).padding(end = 10.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)){
        
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.topbarcolor))
            .clickable{
                if(isClickable) payVm.makeTransaction(user)
            },
            contentAlignment = Alignment.Center) {

           Row(Modifier.padding(start = 10.dp).padding(end = 10.dp)) {
               Text(text = "${user.price} DKK", color = Color.Magenta)
               Text(text = " | ")
               Text(text = user.name, color = colorResource(id = R.color.darkorange))
               Text(text = " | ")
               Text(text = "+45 ${user.phone}", color = Color.Cyan)
           }

        }
        }
    Spacer(modifier = Modifier.height(10.dp))
    
}













