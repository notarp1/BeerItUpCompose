package com.notarmaso.beeritup

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.notarmaso.beeritup.db.repositories.KitchenRepository
import com.notarmaso.beeritup.db.repositories.UserRepository
import com.notarmaso.beeritup.models.*
import com.notarmaso.beeritup.models.Kitchen
import com.notarmaso.beeritup.models.KitchenLoginObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class Service(
    ctx: Context,
    val stateHandler: StateHandler,
    val observer: Observer,
    private val kitchenRepo: KitchenRepository,
    private val userRepo: UserRepository,
) {

    var nav: NavHostController? = null
    val context = ctx


    private var _currentPage: Pages = Pages.MAIN_MENU
    val currentPage: Pages get() = _currentPage

    private lateinit var _selectedBeverage: BeverageType
    val selectedBeverage: BeverageType get() = _selectedBeverage

    fun setBeverageType(beverage: BeverageType) {
        _selectedBeverage = beverage
    }

    fun setCurrentPage(page: Pages) {
        _currentPage = page
    }

    fun navigate(location: Pages) {
        CoroutineScope(Dispatchers.Main).launch {
            nav?.navigate(location.value)
        }
    }


    fun navigateAndClearBackstack(location: Pages) {
        CoroutineScope(Dispatchers.Main).launch {
            nav?.backQueue?.clear()
            nav?.navigate(location.value)
        }
    }


    /*
   *
   * LOGIN USER
   *
   */

    suspend fun logInUser(email: String, password: String) {

        val res: Response<UserRecieve>
        val userLoginObj = UserLoginObject(email, password)
        withContext(Dispatchers.IO) { res = userRepo.login(userLoginObj) }

        if (res.isSuccessful){
            makeToast("Login Succesful!")
            handleUser(res)
        }
        else makeToast(res.message())


    }


    private suspend fun handleUser(res: Response<UserRecieve>) {

        val user: UserRecieve? = res.body()

        withContext(Dispatchers.IO) {
            if (user != null) {
                val userDetails = stateHandler.getAssignedDetails(user.id)

                if (userDetails != null) {
                    stateHandler.onUserSignInSuccess(user, userDetails)
                }
            }
        }

        navigateAndClearBackstack(Pages.MAIN_MENU)
    }


    /*
    *
    * LOGIN KITCHEN
    *
    */

    suspend fun logInKitchen(name: String, password: String) {
        val res: Response<Kitchen>
        val kitchenLoginObj = KitchenLoginObject(name, password)

        withContext(Dispatchers.IO) {
            res = kitchenRepo.login(kitchenLoginObj)
        }

        if(res.isSuccessful){
            handleKitchen(res)
            makeToast("Login Succesful!")

        } else makeToast(res.message())




    }




    private suspend fun handleKitchen(res: Response<Kitchen>) {
        val kitchen: Kitchen? = res.body()

        withContext(Dispatchers.IO) {
            if (kitchen != null) {
                stateHandler.onKitchenSignInSuccess(kitchen)
            }
        }

        navigateAndClearBackstack(Pages.MAIN_MENU)


    }



    fun makeToast(msg: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }


}



