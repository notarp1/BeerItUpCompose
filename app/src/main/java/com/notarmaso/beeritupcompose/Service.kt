package com.notarmaso.beeritupcompose

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.notarmaso.beeritupcompose.models.*


class Service(ctx: Context, val stateHandler: StateHandler, val observer: Observer) {

  var nav: NavHostController? = null
  val context = ctx


  private var _currentPage: Pages = Pages.MAIN_MENU
  val currentPage: Pages get() = _currentPage

  private lateinit var _selectedBeverage: BeverageType
  val selectedBeverage: BeverageType get() = _selectedBeverage


  fun setBeverageType(beverage: BeverageType) {
    _selectedBeverage = beverage
  }

  fun setCurrentPage(page: Pages){
    _currentPage = page
  }

  fun navigate(location: Pages){
    nav?.navigate(location.value)
  }


  fun navigateAndClearBackstack(location: Pages){
    nav?.backQueue?.clear()
    nav?.navigate(location.value)
  }



  /* Insert Into Other Support Class*/
  fun createAlertBoxSelectBeer(beerQty: Int, price: Float, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("FIXME you are selecting $beerQty beers")
      .setMessage("For at total of $price DKK \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
      makeToast("Succesfully bought beers")
      onAccept()

    }

    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->

    }
    alertDialogBuilder.show()


  }

  fun createAlertBoxAddBeer(price: Float, onAccept: () -> Unit, qty: Int){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("FIX ME you are adding $qty beers!")
      .setMessage("For at price of $price DKK/pcs \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
      onAccept()
      makeToast("Succesfully added beers")

    }

    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
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



