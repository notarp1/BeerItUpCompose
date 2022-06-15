package com.notarmaso.beeritup.views.beeritup.select_beverage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.notarmaso.beeritup.Category
import com.notarmaso.beeritup.Pages
import com.notarmaso.beeritup.R
import com.notarmaso.beeritup.models.BeverageType
import com.notarmaso.beeritup.ui.theme.components.ButtonMain
import com.notarmaso.beeritup.ui.theme.components.TopBar
import com.notarmaso.beeritup.views.beeritup.select_beverage.SelectBeverageViewModel


@Composable
fun SelectBeverage(selectBeverageViewModel: SelectBeverageViewModel) {
    val vm = selectBeverageViewModel


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
        val (beverageList, selectionRow, topBar, divider) = createRefs()

        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        }, "select beverage", goTo = { vm.s.nav?.popBackStack() }, Icons.Rounded.ArrowBack)

        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(selectionRow) {
                    top.linkTo(topBar.bottom, 10.dp)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            ButtonMain(
                onClick = {vm.setCategory(Category.BEERS)},
                text = "beers",
                isInverted =  vm.selectedCategory == Category.BEERS.category,
                height = 40.dp,
                widthScale = 0.30 )

            ButtonMain(
                onClick = {vm.setCategory(Category.SOFT_DRINKS)},
                text = "soft drinks",
                isInverted = vm.selectedCategory == Category.SOFT_DRINKS.category,
                height = 40.dp, widthScale = 0.30 )

        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(MaterialTheme.colors.onPrimary).constrainAs(divider){
            top.linkTo(selectionRow.bottom, 10.dp)
        })

        BeverageList(vm = selectBeverageViewModel,
            Modifier.constrainAs(beverageList) {
                    top.linkTo(divider.bottom)
                })

    }

}


@Composable
private fun BeverageList(vm: SelectBeverageViewModel, modifier: Modifier = Modifier) {
    val beverageTypes = vm.beveragesInStock
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    if(vm.beveragesInStock.isEmpty()){

        Box(modifier = Modifier.height((height - 140).dp), contentAlignment = Alignment.Center){
            Text(text = "Hmmm... It seems empty here!\nAdd some beverages to get going!", style = MaterialTheme.typography.h3, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        }
    } else {
        Box(
            modifier = modifier
                .background(Color.Transparent)
                .height(height.dp - 130.dp)
        ) {

            LazyColumn(contentPadding = PaddingValues(5.dp, 10.dp, 5.dp, 10.dp)) {
                items(beverageTypes) { beverage ->
                    BeverageCard(vm = vm, beverageType = beverage)
                }
            }

        }
    }
}


@Composable
private fun BeverageCard(vm: SelectBeverageViewModel, beverageType: BeverageType){
    Surface(
        shape = RoundedCornerShape(50.dp),
        elevation = 1.dp,
        border = BorderStroke(4.dp, color = colorResource(id = R.color.colorLightGreen) ),
        color = colorResource(id = R.color.colorBlackGrey),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)


    ) {
        Row(
            Modifier
                .height(80.dp)
                .clickable(onClick = {
                    vm.navToNextPage(Pages.SELECT_BEVERAGE_STAGE_2, beverageType)
                    vm.hasRun = false
                }
                ), verticalAlignment = Alignment.CenterVertically
        ) {

            BeerRowImage(beverageType = beverageType)

            Spacer(modifier = Modifier.width(35.dp))
            Column() {
                Text(
                    text = beverageType.name,
                    style = MaterialTheme.typography.h1,
                    color = colorResource(id = R.color.colorLightGreen),

                    )

                Text(
                    text = beverageType.stock.toString() +" pcs. in stock",
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.colorLightGreen),

                    )
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun BeerRowImage(beverageType: BeverageType){
    val painter = rememberImagePainter(
        data = "https://beeritupstorage.s3.eu-central-1.amazonaws.com/beertypes/" + beverageType.pictureUrl,
        builder = {

        }
    )
    val painterState = painter.state
    ConstraintLayout(Modifier.rotate(90f)) {

        val picture = createRef()

        Image(painter = painter, contentDescription = beverageType.name, modifier = Modifier.constrainAs(picture){

        })

        if (painterState is ImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
    }


}