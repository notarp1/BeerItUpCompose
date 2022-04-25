package com.notarmaso.beeritupcompose

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.NavHostController
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.models.*

import kotlinx.datetime.*


class Service(ctx: Context, val observer: Observer) {


  private var _currentPage: Pages = Pages.MAIN_MENU
  val currentPage: Pages get() = _currentPage

  private var _currentDate: String = Clock.System.todayAt(TimeZone.currentSystemDefault()).month.toString()
  val currentDate: String get() = _currentDate

  val context = ctx

  var navHostController: NavHostController? = null
  var sharedPref: SharedPreferences

  lateinit var currentUser: User
  var latestUser: User? = null
  var selectedGlobalBeer: GlobalBeer = SampleData.beerListSample[0]

  private val beerRepository: BeerRepository = BeerRepository(context)

  init {
    val activity: Activity = context as Activity
    sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
  }

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


  fun updateTotalAddedBeersPrefs(added: Float) {
    val oldPrice = sharedPref.getFloat("TotalAdded", 0f)
    with (sharedPref.edit()) {
      putFloat("TotalAdded", (oldPrice + added).roundOff().toFloat())
      apply()
    }
  }

  fun updateTotalBoughtBeersPrefs(paid: Float) {
    val oldPrice = sharedPref.getFloat("TotalBought", 0f)
    with (sharedPref.edit()) {
      putFloat("TotalBought", (oldPrice + paid).roundOff().toFloat())
      apply()

    }
  }

  fun resetPrefs(){
    with (sharedPref.edit()) {
      putFloat("TotalBought", 0f)
      putFloat("TotalAdded", 0f)
      apply()
    }
  }

  suspend fun calcBeerDifference(): Map<String, Float> {
    var totalLeft = 0f
    val totalSpent = sharedPref.getFloat("TotalBought", 0f)
    val totalAdded = sharedPref.getFloat("TotalAdded", 0f)




    val list: MutableList<BeerGroup> = beerRepository.getAllBeerGroups()

    for (x in list) {
      val beers: MutableList<Beer>? = deserializeBeerGroup(beers = x.beers)
      if (beers != null) {
        for (beer in beers) {
          totalLeft += beer.price
        }
      }
    }

    return mapOf(
      "TotalSpent" to totalSpent,
      "TotalAdded" to totalAdded,
      "TotalLeft" to totalLeft
    )
  }

  /* Insert Into Other Support Class*/
  fun createAlertBoxSelectBeer(beerQty: Int, price: Float, onAccept: () -> Unit){
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
      .setTitle("${currentUser.name} you are selecting $beerQty beers")
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
      .setTitle("${currentUser.name} you are adding $qty beers!")
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



