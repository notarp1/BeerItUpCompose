package com.notarmaso.beeritup.views.beeritup.select_beverage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.notarmaso.beeritup.models.Beverage
import com.notarmaso.beeritup.models.BeverageType

import com.notarmaso.beeritup.ui.theme.components.*



@Composable
fun SelectBeverageQuantity(bevQtyViewModel: SelectBeverageQuantityViewModel){
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

        val (bevImage, topBar, selectionRow, confirmBtn, latestBeers) = createRefs()
        val titleText = createRef()
        TopBar(
            Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
            },
            "select ${bevType.name}",
            goTo = { bevQtyViewModel.s.nav?.popBackStack()},
            Icons.Rounded.ArrowBack
        )


            BeerImage(
            beverageType = bevType,
            modifier = Modifier.constrainAs(bevImage) {
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
            ButtonSelection(onClick = { bevQtyViewModel.decrementCounter() },  widthScale = 0.33)
            Text(text = bevQtyViewModel.qtySelected.toString(), style = MaterialTheme.typography.h1, textAlign = TextAlign.Center, modifier = Modifier.width(80.dp))
            ButtonSelection(onClick = { bevQtyViewModel.incrementCounter() },  widthScale = 0.33, true)
            Spacer(modifier = Modifier.width(10.dp))

        }

        LazyColumn(modifier = Modifier
            .height(150.dp)
            .constrainAs(latestBeers) {
                centerHorizontallyTo(parent)
                bottom.linkTo(confirmBtn.top, 10.dp)

            }, contentPadding = PaddingValues(5.dp, 10.dp, 5.dp,10.dp)){

            val priceList = bevQtyViewModel.beveragePriceList

            if (priceList != null) {
                items(priceList) { entry ->
                    BeverageEntryCard(beverage = entry, bevType.name)
                }
            }

        }



        ConfirmButton(bevQtyViewModel, confirmBtn)


    }
}

@Composable
fun BeverageEntryCard(beverage: Beverage, name: String){
    val price = beverage.price/100
    val priceString = "$price DKK"

    Surface(modifier = Modifier
        .width(300.dp)
        .height(30.dp)
        .padding(start = 5.dp)
        .padding(end = 5.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(10.dp)
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center) {

            Row{
                Text(text = "$name $priceString", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}


@Composable
private fun ConstraintLayoutScope.ConfirmButton(
    vm: SelectBeverageQuantityViewModel,
    confirmBtn: ConstrainedLayoutReference
) {
    val configuration = LocalConfiguration.current
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }

    var openDialog by remember { mutableStateOf(false) }

    CustomAlertDialog(
        isOpened = openDialog,
        onClose = { openDialog = !openDialog },
        onConfirm = { vm.onConfirm() },
        title = "Confirm Purchase, ${vm.s.stateHandler.appMode.uName}?",
        text = "You are buying ${vm.qtySelected} ${vm.s.selectedBeverage.name} for ${vm.price} DKK",
        onOpened = { })


    SubmitButton(Modifier.Companion.constrainAs(confirmBtn) {
        bottom.linkTo(parent.bottom)
    }, "CONFIRM", height(0.15).dp) {
        vm.getPriceClicked()
        openDialog = true }
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

            Image(painter = painter, contentDescription = beverageType.name, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp))

            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator()
            }
        }


}

