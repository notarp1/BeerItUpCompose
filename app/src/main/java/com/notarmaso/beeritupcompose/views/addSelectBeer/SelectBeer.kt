package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.SampleData
import org.koin.androidx.compose.get

@Composable
fun SelectBeer(viewModel: SelectBeerViewModel){
    Column{
        TopBar("Select Beer!", Icons.Rounded.ArrowBack) {
            viewModel.navigateBack(MainActivity.SELECT_USER)
        }
        BeerList(globalBeers = SampleData.beerListSample, viewModel)
    }
}


@Composable
fun BeerList(globalBeers: List<GlobalBeer>, selectBeerViewModel: SelectBeerViewModel) {
    var isAddingBeer by remember{ mutableStateOf(false) }
    val service = get<Service>()

    if(service.currentPage == MainActivity.ADD_BEER) isAddingBeer =true

    Box(modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.background))) {
        LazyColumn(
        ) {
            items(globalBeers) { beer ->
                val beerStock = selectBeerViewModel.getStock(beer.name)
                if (beerStock > 0 || isAddingBeer) BeerCard(beer, selectBeerViewModel, beerStock)
            }
        }
    }
}


@Composable
fun BeerCard(globalBeer: GlobalBeer = SampleData.beerListSample[0], viewModel: SelectBeerViewModel, beerStock: Int) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        elevation = 1.dp,
        color = colorResource(id = R.color.buttonColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),


    ) {
        Row(
            Modifier
                .padding(all = 8.dp)
                .clickable(onClick = {
                    viewModel.setBeer(globalBeer = globalBeer)
                    viewModel.navigate(MainActivity.SELECT_BEER_QUANTITY)
                }
                )
        ) {
            Image(
                painter = painterResource(id = globalBeer.image),
                contentDescription = "CurrentBeer",
                modifier = Modifier
                    .size(70.dp)
                    .padding(3.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column(verticalArrangement = Arrangement.Top) {
                Text(
                    text = globalBeer.name,
                    color = colorResource(id = R.color.topbarcolor),
                    fontSize = 25.sp,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Antal: $beerStock stk.",
                    color = colorResource(id = R.color.topbarcolor),
                    fontSize = 18.sp
                )
            }
        }
    }
}
