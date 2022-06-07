package com.notarmaso.db_access_setup.views.beeritup.add_beverage


import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.notarmaso.db_access_setup.MainActivity
import com.notarmaso.db_access_setup.R
import com.notarmaso.db_access_setup.models.BeverageType
import com.notarmaso.db_access_setup.ui.theme.components.ButtonMain
import com.notarmaso.db_access_setup.ui.theme.components.TopBar
import com.notarmaso.db_access_setup.views.beeritup.Category


@Composable
fun AddBeverage(addBeverageViewModel: AddBeverageViewModel) {

    val vm = addBeverageViewModel
    /*TODO: Remake this with observer*/
    if(!addBeverageViewModel.hasrun) {
        addBeverageViewModel.getBeverageTypes()
        addBeverageViewModel.hasrun = true
    }
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
        var beerIsSelected by remember{ mutableStateOf(true)}
        TopBar(Modifier.constrainAs(topBar){
            top.linkTo(parent.top)
        }, "add beverage", goTo = { addBeverageViewModel.navController.popBackStack() }, Icons.Rounded.ArrowBack)

        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(selectionRow) {
                    top.linkTo(topBar.bottom, 10.dp)
                },
            horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
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
        Divider(Modifier.constrainAs(divider){
            top.linkTo(selectionRow.bottom, 10.dp)
        })

        BeverageList(vm = addBeverageViewModel, Modifier.constrainAs(beverageList){
            top.linkTo(divider.bottom)

        })

    }




}

@Composable
private fun BeverageList(vm: AddBeverageViewModel, modifier: Modifier = Modifier) {
    val beverageTypes = vm.beverageTypes
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    Box(modifier = modifier
        .background(Color.Transparent)
        .height(height.dp - 140.dp)) {

        LazyColumn(){

            items(beverageTypes){beverage ->
                BeverageCard(vm = vm, beverageType = beverage)
            }
        }

    }
}


@Composable
private fun BeverageCard(vm: AddBeverageViewModel, beverageType: BeverageType, modifier: Modifier = Modifier){
    Surface(
        shape = RoundedCornerShape(50.dp),
        elevation = 1.dp,
        border = BorderStroke(4.dp, color = colorResource(id = R.color.colorLightGreen) ),
        color = colorResource(id = R.color.colorBlackGrey),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)


        ) {
        Row(
            Modifier
                .height(80.dp)
                .clickable(onClick = {
                    vm.navToNextPage(MainActivity.ADD_BEVERAGE_2, beverageType)
                    vm.hasrun = false
                }
                ), verticalAlignment = Alignment.CenterVertically
        ) {

            BeerRowImage(beverageType = beverageType)

            Spacer(modifier = Modifier.width(35.dp))

                Text(
                    text = beverageType.name,
                    style = MaterialTheme.typography.h1,
                    color = colorResource(id = R.color.colorLightGreen),

                )


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

        val (picture, topBar) = createRefs()

        Image(painter = painter, contentDescription = beverageType.name, modifier = Modifier.constrainAs(picture){

        })

        if (painterState is ImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
    }


}