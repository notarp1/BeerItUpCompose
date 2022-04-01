package com.notarmaso.beeritupcompose

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.room.Room
import com.google.gson.Gson
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Service(ctx: Context, val userObs: UserObserverNotifier, val paymentObs: PaymentObserverNotifier) {

  var currentUser: User? = null
  var selectedGlobalBeer: GlobalBeer? = null
  var currentPage: String? = null
  var navHostController: NavHostController? = null


  private val context = ctx

  val db = Room.databaseBuilder(
    ctx,
    AppDatabase::class.java, "BeerItUpDB"
  ).fallbackToDestructiveMigration().build()

  fun navigate(location: String){
    navHostController?.navigate(location)
  }

  fun navigateBack(location: String){
    navHostController?.navigate(location){popUpTo(location)}
  }



  fun createAlertBoxSelectBeer(beerQty: Int, price: Float, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser?.name} you are selecting $beerQty beers")
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
      .setTitle("${currentUser?.name} you are adding $qty beers!")
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

  fun makeToast(msg: String){
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
  }



}



