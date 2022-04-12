package com.notarmaso.beeritupcompose.views.userSelection

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.MainActivity
import com.notarmaso.beeritupcompose.Pages
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.Service
import com.notarmaso.beeritupcompose.components.TopBar
import com.notarmaso.beeritupcompose.models.User
import org.koin.androidx.compose.get

@Composable
fun SelectUser(viewModel: SelectUserViewModel){

    Column{
        TopBar("Select User!", Icons.Rounded.ArrowBack) {
            viewModel.navigateBack(Pages.MAIN_MENU)
        }

        UserList(users = viewModel.users, viewModel = viewModel )

       // UserList(users = SampleData.userListSample, viewModel)
    }
}



@Composable
fun UserList(users: List<User>, viewModel: SelectUserViewModel) {

    val currentPage = get<Service>().currentPage
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background))){

        LazyColumn (modifier = Modifier.padding(top = 20.dp)) {

            items(users) { user ->
                UserCard(user, viewModel, currentPage)
            }
        }
    }

}


@Composable
fun UserCard(user: User, vm: SelectUserViewModel, currentPage: Pages){

    Surface(
        shape = RoundedCornerShape(50),
        elevation = 1.dp,
        color = colorResource(id = R.color.buttonColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(60.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable {
                    vm.service.currentUser = user
                    when(currentPage){
                        Pages.BUY_BEER -> {
                            vm.service.observer.notifySubscribers("UPDATE_BEER_LIST")
                            vm.navigate(Pages.BUY_BEER)}
                        Pages.ADD_BEER -> {
                            vm.service.observer.notifySubscribers("UPDATE_BEER_LIST")
                            vm.navigate(Pages.BUY_BEER)}
                        Pages.PAYMENTS -> {
                            vm.service.observer.notifySubscribers(Pages.PAYMENTS.value)
                            vm.navigate(Pages.PAYMENTS)
                        }
                        Pages.LOG_BOOKS-> {
                            vm.service.observer.notifySubscribers(Pages.LOG_BOOKS.value)
                            vm.navigate(Pages.LOG_BOOKS)
                        }
                        else -> vm.navigateBack(Pages.MAIN_MENU)
                    }
                           },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = user.name,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.topbarcolor),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }


    }
}
