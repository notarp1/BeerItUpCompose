package com.notarmaso.db_access_setup.views.beeritup.select_beverage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.notarmaso.db_access_setup.models.BeverageType
import com.notarmaso.db_access_setup.ui.theme.components.ButtonMain
import com.notarmaso.db_access_setup.ui.theme.components.ButtonSelection
import com.notarmaso.db_access_setup.ui.theme.components.SubmitButton
import com.notarmaso.db_access_setup.ui.theme.components.TopBar

@Composable
fun SelectBeverageQuantity(bevQtyViewModel: SelectBeverageQuantityViewModel){
    val bevType: BeverageType = bevQtyViewModel.service.selectedBeverage
    /*TODO run with observer*/
    bevQtyViewModel.setup(bevType.stock)


    val configuration = LocalConfiguration.current
    fun height(widthScale: Double): Double { return configuration.screenHeightDp * widthScale }


    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colors.background,
                MaterialTheme.colors.secondary
            )), alpha = 0.9f)
    ) {

        val (bevImage, topBar, selectionRow, confirmBtn) = createRefs()
        val titleText = createRef()
        TopBar(
            Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
            },
            "select ${bevType.name}",
            goTo = { bevQtyViewModel.navController.popBackStack()},
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
            ButtonSelection(onClick = { bevQtyViewModel.decrementCounter()},  widthScale = 0.33)
            Text(text = bevQtyViewModel.qtySelected.toString(), style = MaterialTheme.typography.h1, textAlign = TextAlign.Center, modifier = Modifier.width(80.dp))
            ButtonSelection(onClick = { bevQtyViewModel.incrementCounter() },  widthScale = 0.33, true)
            Spacer(modifier = Modifier.width(10.dp))

        }



        SubmitButton(Modifier.constrainAs(confirmBtn) {
            bottom.linkTo(parent.bottom)
        }, "CONFIRM", height(0.15).dp) { bevQtyViewModel.onConfirm() }



    }
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