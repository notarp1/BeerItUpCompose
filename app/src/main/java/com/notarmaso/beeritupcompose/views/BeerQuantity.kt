package com.notarmaso.beeritupcompose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.SampleData


@Composable
fun BeerQuantityPage(viewModel: BeerQuantityViewModel){

    Column{
        TopBar("Select Beer!", Icons.Rounded.ArrowBack) {
            viewModel.navigateBack(MainActivity.SELECT_BEER)
        }
        viewModel.service.selectedGlobalBeer?.let { BeerQuantity(it, viewModel) }
    }

}


@Composable
fun BeerQuantity(globalBeer: GlobalBeer = SampleData.beerListSample[1], viewModel: BeerQuantityViewModel) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = globalBeer.image),
                contentDescription = globalBeer.name,
                modifier = Modifier
                    .size(300.dp)
                    .padding(20.dp)
            )
            Text(
                text = globalBeer.name,
                color = colorResource(id = R.color.darkorange),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold)

        }
    }

}