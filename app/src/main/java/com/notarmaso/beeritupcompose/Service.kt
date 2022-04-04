package com.notarmaso.beeritupcompose

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.User
import com.notarmaso.beeritupcompose.models.UserEntry


class Service(ctx: Context, val userObs: UserObserverNotifier, val paymentObs: PaymentObserverNotifier) {

  lateinit var currentUser: User
  var selectedGlobalBeer: GlobalBeer? = null
  var currentPage: String? = null
  var navHostController: NavHostController? = null


  val context = ctx


  fun navigate(location: String){
    navHostController?.navigate(location)
  }

  fun navigateBack(location: String){
    navHostController?.navigate(location){popUpTo(location)}
  }



  fun createAlertBoxSelectBeer(beerQty: Int, price: Float, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser.name} you are selecting $beerQty beers")
      .setMessage("For at total of $price DKK \nDo you really want to continue?")

    alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
      makeToast("Succesfully bought beers")
      onAccept()
      navigateBack(MainActivity.MAIN_MENU)
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
      navigateBack(MainActivity.MAIN_MENU)
    }

    alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->
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

  fun makeToast(msg: String){
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
  }



}



