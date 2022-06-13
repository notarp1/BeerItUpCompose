package com.notarmaso.db_access_setup.views.beeritup.add_beverage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.notarmaso.beeritupcompose.models.BeverageType
import com.notarmaso.beeritupcompose.ui.theme.components.ButtonSelection
import com.notarmaso.beeritupcompose.ui.theme.components.CustomAlertDialog
import com.notarmaso.beeritupcompose.ui.theme.components.SubmitButton
import com.notarmaso.beeritupcompose.ui.theme.components.TopBar


@Composable
fun AddBeverageQuantity(bevQtyViewModel: AddBeverageQuantityViewModel){
    val vm = bevQtyViewModel

    val bevType: BeverageType = bevQtyViewModel.s.selectedBeverage


    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colors.background,
                MaterialTheme.colors.onPrimary
            )), alpha = 0.9f)
    ) {

        val (bevImage, topBar, selectionRow, confirmBtn) = createRefs()
        val (titleText, paidRow) = createRefs()
        TopBar(
            Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
            },
            "add ${bevType.name}",
            goTo = { vm.s.nav?.popBackStack()},
            Icons.Rounded.ArrowBack
        )


        BeerImage(beverageType = bevType, modifier = Modifier.constrainAs(bevImage){
            top.linkTo(topBar.bottom, 60.dp)
            centerHorizontallyTo(parent)

        })



        Text(text = bevType.name, style = MaterialTheme.typography.h1, modifier = Modifier.constrainAs(titleText) {
            top.linkTo(bevImage.bottom, 40.dp)
            centerHorizontallyTo(parent)

        })

        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .constrainAs(selectionRow) {
                top.linkTo(titleText.bottom, 20.dp)
            }) {
            Spacer(modifier = Modifier.width(10.dp))
            ButtonSelection(onClick = { vm.decrementCounter()},  widthScale = 0.33)
            Text(text = vm.qtySelected.toString(), style = MaterialTheme.typography.h1, textAlign = TextAlign.Center, modifier = Modifier.width(80.dp))
            ButtonSelection(onClick = { vm.incrementCounter() },  widthScale = 0.33, true)
            Spacer(modifier = Modifier.width(10.dp))

        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .constrainAs(paidRow) {
                top.linkTo(selectionRow.bottom, 20.dp)
            }) {
            Text(text = "You've Paid:     ", color=MaterialTheme.colors.primary)

            TextField(value = vm.pricePaid,
                shape = RoundedCornerShape(20.dp),
                onValueChange = { newValue -> vm.setPricePaidTxt(newValue) },
                singleLine = true,
                placeholder = { Text(text = "Price Paid") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = if(vm.pricePaid == "") Modifier.width(120.dp) else Modifier.width(60.dp))

            Text(text = "     DKK Total", color=MaterialTheme.colors.primary)

        }


        AddBeerButton(vm, confirmBtn)


    }
}

@Composable
private fun ConstraintLayoutScope.AddBeerButton(
    vm: AddBeverageQuantityViewModel,
    confirmBtn: ConstrainedLayoutReference
) {
    val configuration = LocalConfiguration.current
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }

    var openDialog by remember { mutableStateOf(false) }

    CustomAlertDialog(
        isOpened = openDialog,
        onClose = { openDialog = !openDialog },
        onConfirm = { vm.onConfirm() },
        title = "Confirm your added beverages, ${vm.s.stateHandler.appMode.uName}?",
        text = "You are adding ${vm.qtySelected} ${vm.s.selectedBeverage.name}('s) for ${vm.pricePerBeverage} DKK per beverage",
        onOpened = { vm.onClick() })


    SubmitButton(Modifier.Companion.constrainAs(confirmBtn) {
        bottom.linkTo(parent.bottom)
    }, "CONFIRM", height(0.15).dp) { openDialog = true }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun BeerImage(beverageType: BeverageType, modifier: Modifier = Modifier){
    val painter = rememberImagePainter(
        data = "https://beeritupstorage.s3.eu-central-1.amazonaws.com/beertypes/" + beverageType.pictureUrl,
        builder = {

        }
    )
    val painterState = painter.state
    ConstraintLayout(modifier = modifier) {

            Image(painter = painter, contentDescription = beverageType.name, modifier = Modifier.fillMaxWidth().height(200.dp))

            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator()
            }
        }


}