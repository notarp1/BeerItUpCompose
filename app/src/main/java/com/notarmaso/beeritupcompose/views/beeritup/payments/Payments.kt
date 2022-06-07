package com.notarmaso.db_access_setup.views.beeritup.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.db_access_setup.models.UserPaymentObject
import com.notarmaso.db_access_setup.ui.theme.components.TopBar

@Composable
fun Payments(paymentsViewModel: PaymentsViewModel) {
    paymentsViewModel.loadLists()
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
        val (payments, topBar) = createRefs()

        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        }, "Payments", goTo = { paymentsViewModel.navController.popBackStack() }, Icons.Rounded.ArrowBack)

        PaymentsPage(payVm = paymentsViewModel, modifier = Modifier.constrainAs(payments){
            top.linkTo(topBar.bottom)
            centerHorizontallyTo(parent)
        })

    }

}



@Composable
fun PaymentsPage(payVm: PaymentsViewModel, modifier: Modifier = Modifier){
    val configuration = LocalConfiguration.current
    val maxHeight = configuration.screenHeightDp / 2

    Column(modifier = modifier) {
        payVm.owesTo?.let { OwesToList(payVm = payVm, it) }
        payVm.owedFrom?.let { OwedFromList(payVm = payVm, it) }
    }

}


@Composable
fun OwesToList(payVm: PaymentsViewModel, paymentObjectList: List<UserPaymentObject>){

    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
        items(paymentObjectList) { user ->
            UserPaymentCard(user, payVm = payVm, modifier = Modifier.clickable{payVm.makePayment(user.uId)})

        }
    }

}

@Composable
fun OwedFromList(payVm: PaymentsViewModel, paymentObjectList: List<UserPaymentObject>){

    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(paymentObjectList) { user ->
          UserPaymentCard(user, payVm)

        }
    }

}




@Composable
fun UserPaymentCard(user: UserPaymentObject ,payVm: PaymentsViewModel, modifier: Modifier = Modifier){

    Surface(modifier = Modifier
        .width(360.dp)
        .height(45.dp)
        .padding(start = 5.dp)
        .padding(end = 5.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ){

        Box(modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart) {

            Row(
                Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .padding(end = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween

            ) {


                Text(text = "${(user.total/100).toFloat()}", style = MaterialTheme.typography.h4, modifier = Modifier.width(60.dp))
                Text(text = user.name, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Normal, modifier = Modifier.width(140.dp), textAlign = TextAlign.Center)
                Text(text = user.phone, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Normal, modifier = Modifier.width(160.dp), textAlign = TextAlign.Center)
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}