package com.notarmaso.beeritupcompose

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.SampleData
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserEntry
import kotlinx.datetime.*


class Service(ctx: Context, val observer: Observer) {

  lateinit var currentUser: User
  var latestUser: User? = null
  var selectedGlobalBeer: GlobalBeer = SampleData.beerListSample[0]


  val context = ctx

  private var _currentPage: Pages = Pages.MAIN_MENU
  val currentPage: Pages get() = _currentPage


  private var _currentDate: String = Clock.System.todayAt(TimeZone.currentSystemDefault()).month.toString()
  val currentDate: String get() = _currentDate

  var navHostController: NavHostController? = null



  fun setCurrentPage(page: Pages){
    _currentPage = page
  }

  fun getDateMonth(){
    _currentDate = Clock.System.todayAt(TimeZone.currentSystemDefault()).month.toString()
  }

  fun navigate(location: Pages){
    navHostController?.navigate(location.value)
  }

  fun navigateBack(location: Pages){
    navHostController?.navigate(location.value){popUpTo(location.value)}
  }



  fun createAlertBoxSelectBeer(beerQty: Int, price: Float, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser.name} you are selecting $beerQty beers")
      .setMessage("For at total of $price DKK \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
      makeToast("Succesfully bought beers")
      onAccept()

    }

    alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->

    }
    alertDialogBuilder.show()


  }

  fun createAlertBoxAddBeer(price: Float, onAccept: () -> Unit, qty: Int){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser.name} you are adding $qty beers!")
      .setMessage("For at price of $price DKK/pcs \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
      onAccept()
      makeToast("Succesfully added beers")

    }

    alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->
      makeToast("Cancelled")
    }
    alertDialogBuilder.show()


  }

  fun createAlertBoxDeleteUser(onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("DELETE USER")
      .setMessage("ARE YOU SURE YOU WANT TO DELETE USER ${currentUser.name} \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton("YES DELETE") { _, _ ->
      onAccept()
      makeToast("Deleting User")
    }

    alertDialogBuilder.setNegativeButton("NO GO BACK") { _, _ ->
      makeToast("Cancelled")
    }
    alertDialogBuilder.show()


  }

  fun createAlertBoxPayment(user: UserEntry, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser.name} you are sending money!")
      .setMessage("You must send ${user.price} DKK to ${user.name} with phone: ${user.phone}")

    alertDialogBuilder.setPositiveButton("I have sent the money!") { _, _ ->
      onAccept()
      makeToast("Succesfully paid money")
    }

    alertDialogBuilder.setNegativeButton("Wait!") { _, _ ->
      makeToast("Cancelled")
    }
    alertDialogBuilder.show()


  }

  fun createAlertBoxAddUser(user: User, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("Is this correct?")
      .setMessage("Username: ${user.name} \nPhone: ${user.phone}")

    alertDialogBuilder.setPositiveButton("Yes create user!") { _, _ ->
      onAccept()
    }

    alertDialogBuilder.setNegativeButton("Wait, go back!!") { _, _ ->
      makeToast("Cancelled")
    }
    alertDialogBuilder.show()
  }

  fun makeToast(msg: String){
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
  }



}



