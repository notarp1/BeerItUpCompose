package com.notarmaso.beeritup.views.beeritup.payments

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.notarmaso.beeritup.Category
import com.notarmaso.beeritup.models.UserPaymentObject
import com.notarmaso.beeritup.ui.theme.components.ButtonMain
import com.notarmaso.beeritup.ui.theme.components.CustomAlertDialog
import com.notarmaso.beeritup.ui.theme.components.TopBar


@Composable
fun Payments(paymentsViewModel: PaymentsViewModel) {

    val vm = paymentsViewModel



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
        val (payments, topBar, selectionRow, divider) = createRefs()

        TopBar(Modifier.constrainAs(topBar) {
            top.linkTo(parent.top)
        }, "Payments", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack)

        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(selectionRow) {
                    top.linkTo(topBar.bottom, 10.dp)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonMain(
                onClick = { vm.setCategory(Category.MONEY_YOU_OWE) },
                text = "$ you owe",
                isInverted = vm.selectedCategory == Category.MONEY_YOU_OWE.category,
                height = 40.dp,
                widthScale = 0.40
            )

            ButtonMain(
                onClick = { vm.setCategory(Category.MONEY_YOU_ARE_OWED) },
                text = "$ you are owed",
                isInverted = vm.selectedCategory == Category.MONEY_YOU_ARE_OWED.category,
                height = 40.dp, widthScale = 0.40
            )

        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colors.onPrimary)
                .constrainAs(divider) {
                    top.linkTo(selectionRow.bottom, 10.dp)
                })


        PaymentsPage(payVm = vm, modifier = Modifier.constrainAs(payments) {
            top.linkTo(divider.bottom)
            centerHorizontallyTo(parent)
        })

    }

}


@Composable
fun PaymentsPage(payVm: PaymentsViewModel, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    Column(modifier = modifier) {
        if (payVm.selectedCategory == Category.MONEY_YOU_OWE.category) {
            payVm.owesTo?.let { OwesToList(payVm = payVm, it, height) }
        } else {
            payVm.owedFrom?.let { OwedFromList(it, height) }
        }
    }

}


@Composable
fun OwesToList(payVm: PaymentsViewModel, paymentObjectList: List<UserPaymentObject>, height: Int) {

    if(paymentObjectList.isEmpty()){
        Box(modifier = Modifier.height((height - 140).dp), contentAlignment = Alignment.Center){
            Text(text = "Congrats!\nYou are all settled up!", style = MaterialTheme.typography.h3, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        }
    } else {
        LazyColumn(
            modifier = Modifier.height((height - 140).dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(paymentObjectList) { user ->

                UserPaymentCardOwes(user, payVm)
            }
        }
    }

}

@Composable
fun OwedFromList(
    paymentObjectList: List<UserPaymentObject>,
    height: Int
) {
    if(paymentObjectList.isEmpty()){
        Box(modifier = Modifier.height((height - 140).dp), contentAlignment = Alignment.Center){
            Text(text = "Congrats!\nEveryone has paid up!", style = MaterialTheme.typography.h3, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp)
                .height((height - 90).dp)
        ) {

            items(paymentObjectList) { user ->
                UserPaymentCardOwed(user)

            }
        }
    }

}


@Composable
fun UserPaymentCardOwed(
    user: UserPaymentObject,
    modifier: Modifier = Modifier,
) {

    val price = (user.total/100f)
    Surface(
        modifier = Modifier
            .width(360.dp)
            .height(45.dp)
            .padding(start = 5.dp)
            .padding(end = 5.dp)
            .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {


                Text(
                    text = price.toString(),
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.width(60.dp)
                )
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.width(140.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.width(160.dp),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}


@Composable
fun UserPaymentCardOwes(
    user: UserPaymentObject,
    payVm: PaymentsViewModel
) {
    var openDialog by remember { mutableStateOf(false) }
    val price = (user.total/100f)
    CustomAlertDialog(
        isOpened = openDialog,
        onClose = { openDialog = !openDialog },
        onConfirm = {payVm.onAccept(user.uId)},
        title = "Make payment to ${user.phone} ",
        text = "You have to pay ${user.name}\n${price} DKK on phone ${user.phone} ",
        onOpened = {})

    Surface(
        modifier = Modifier
            .width(360.dp)
            .height(45.dp)
            .padding(start = 5.dp)
            .padding(end = 5.dp)
            .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier
                .clickable { openDialog=true }
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {


                Text(
                    text = price.toString(),
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.width(60.dp)
                )
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.width(140.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.width(160.dp),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}