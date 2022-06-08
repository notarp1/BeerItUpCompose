package com.notarmaso.beeritupcompose

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.*
import com.notarmaso.db_access_setup.models.Kitchen
import com.notarmaso.db_access_setup.models.KitchenLoginObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class Service(ctx: Context, val stateHandler: StateHandler, val observer: Observer) {

  var nav: NavHostController? = null
  val context = ctx


  private var _currentPage: Pages = Pages.MAIN_MENU
  val currentPage: Pages get() = _currentPage

  private lateinit var _selectedBeverage: BeverageType
  val selectedBeverage: BeverageType get() = _selectedBeverage

  private val kitchenRepo = KitchenRepository
  private val userRepo = UserRepository

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


  /*
 *
 * LOGIN USER
 *
 */

  fun logInUser(phone: String, password: String){
    CoroutineScope(Dispatchers.Main).launch {
      val res: Response<UserRecieve>
      val userLoginObj = UserLoginObject(phone, password)
      withContext(Dispatchers.IO){
        res = userRepo.login(userLoginObj)
      }
      handleErrorUser(res)

    }
  }

  private fun handleErrorUser(response: Response<UserRecieve>) {
    when(response.code()){
      200 -> {
        makeToast("Login Succesful!")
        handleUser(response)
      }
      400 -> makeToast("Error: This User Does Not Exist")
      401 -> makeToast("Error: Wrong password")
      500 -> makeToast(response.message())
      else -> makeToast("Error: Unknown")
    }
  }

  private fun handleUser(res: Response<UserRecieve>){
    CoroutineScope(Dispatchers.Main).launch {
      val user: UserRecieve? = res.body()

      withContext(Dispatchers.IO){
        if (user != null) {
          val userDetails = stateHandler.getAssignedDetails(user.id)
          if (userDetails != null) {

            stateHandler.onUserSignInSuccess(user, userDetails)
          }
        }
      }
      navigateAndClearBackstack(Pages.MAIN_MENU)

    }
  }


  /*
  *
  * LOGIN KITCHEN
  *
  */

   fun logInKitchen(name: String, password: String) {
    CoroutineScope(Dispatchers.Main).launch {
      val res: Response<Kitchen>
      val kitchenLoginObj = KitchenLoginObject(name, password)

      withContext(Dispatchers.IO) {
        res = kitchenRepo.login(kitchenLoginObj)
      }
      handleLoginErrorKitchen(res)

    }
  }

  private fun handleLoginErrorKitchen(response: Response<Kitchen>) {
    when (response.code()) {
      200 -> {
        makeToast("Login Succesful!")
        handleKitchen(response)
      }
      400 -> makeToast("Error: This Kitchen Does Not Exist")
      401 -> makeToast("Error: Wrong password")
      500 -> makeToast(response.message())
      else -> makeToast("Error: Unknown: ${response.message()}")
    }
  }


  private fun handleKitchen(res: Response<Kitchen>) {
    CoroutineScope(Dispatchers.Main).launch {
      val kitchen: Kitchen? = res.body()

      withContext(Dispatchers.IO) {
        if (kitchen != null) {
          stateHandler.onKitchenSignInSuccess(kitchen)
        }
      }
      navigateAndClearBackstack(Pages.MAIN_MENU)


    }
  }



  /*INSERT IN CLASS WHERE IT SHOULD BE USED*/
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



