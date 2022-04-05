package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.SampleData
import org.koin.androidx.compose.get


@Composable
fun BeerQuantityPage(viewModel: BeerQuantityViewModel) {
    var isAddingBeer by remember{ mutableStateOf(false)}

    val service = get<Service>()
    val selectedBeer = service.selectedGlobalBeer
    viewModel.setTotalQty(selectedBeer?.name!!)
    if(service.currentPage == MainActivity.ADD_BEER) isAddingBeer = true

    Column {
        TopBar(if(isAddingBeer)"Add qty. of ${selectedBeer.name}" else "Select beers!", Icons.Rounded.ArrowBack) {
            viewModel.navigateBack(MainActivity.SELECT_BEER)
        }
        BeerQuantity(selectedBeer, viewModel, isAddingBeer)
    }

}



@Composable
fun BeerQuantity(
    globalBeer: GlobalBeer = SampleData.beerListSample[1],
    vm: BeerQuantityViewModel,
    addBeer: Boolean
) {


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(colorResource(id = R.color.background)),
        contentAlignment = Alignment.TopCenter) {


        Column (modifier = Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = globalBeer.image),
                    contentDescription = globalBeer.name,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )

                Text(
                    text = globalBeer.name,
                    color = colorResource(id = R.color.darkorange),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    ButtonBeer(false) { vm.decrementCounter() }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${vm.qtySelected.toString()} \n pcs.", color = colorResource(id = R.color.darkorange), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.width(10.dp))
                    ButtonBeer(true){vm.incrementCounter()}
                }

                if(addBeer) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "You've Paid: ", color = colorResource(id = R.color.darkorange))

                        Spacer(modifier = Modifier.width(20.dp))

                        TextField(value = vm.pricePaid,
                            shape = RoundedCornerShape(20.dp),
                            onValueChange = { newNumber -> vm.pricePaid = newNumber },
                            placeholder = { Text(text = "Price Paid") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(100.dp))

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(text = " DKK Total", color = colorResource(id = R.color.darkorange))


                    }
                }

            }

            if(!addBeer) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Beer in stock: ${vm.beerCount}",
                    color = colorResource(id = R.color.topbarcolor),
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            ConfirmButton(vm, addBeer)

            Spacer(modifier = Modifier.height(10.dp))
            
            BeerList(vm = vm)

    
        }
    }
}


@Composable
fun BeerList(vm: BeerQuantityViewModel){
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {

        items(vm.beerList) { beer ->
            BeerListItem(beer, width.toDouble())
        }
    }
}

@Composable
fun BeerListItem(beer: Beer, width: Double){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(30.dp)
        .padding(start = 5.dp)
        .padding(end = 5.dp)
        .background(Color.Transparent), shape = RoundedCornerShape(20.dp)){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.topbarcolor)),
            contentAlignment = Alignment.CenterStart) {

            Row(Modifier
                .padding(start = 20.dp).fillMaxWidth()
                .padding(end = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(text = "${beer.price} DKK", color = colorResource(id = R.color.buttonColor), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = beer.owner, color = colorResource(id = R.color.darkorange))
            }

        }
    }
    Spacer(modifier = Modifier.height(5.dp))

}



@Composable
fun ConfirmButton(vm: BeerQuantityViewModel, addBeer: Boolean) {
    Surface(shape = RoundedCornerShape(20),
        modifier = Modifier
            .background(Color.Transparent)
            .height(40.dp)
            .padding(start = 20.dp)
            .padding(end = 20.dp)) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.background(colorResource(id = R.color.topbarcolor)))
        {
            Button(onClick = { vm.onConfirm(isAddingBeer = addBeer) },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.topbarcolor)),
                modifier = Modifier.fillMaxSize()) {
                Text(text = "CONFIRM", color = colorResource(id = R.color.darkorange), fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun ButtonBeer(plus: Boolean = true, onClick: () -> Unit) {
    Surface(shape = RoundedCornerShape(20),
        modifier = Modifier
            .background(Color.Transparent)
            .height(70.dp)
            .width(70.dp)) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.background(colorResource(id = R.color.buttonColor))) {
            Button(
                onClick = { onClick()},
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.buttonColor)),
                modifier = Modifier.fillMaxSize())
            {
                Text(text = if (plus) "+" else "-", fontSize = 35.sp, fontWeight = FontWeight.Light)
            }
        }


    }
}