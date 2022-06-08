package com.notarmaso.beeritupcompose

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.notarmaso.beeritupcompose.db.repositories.KitchenRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.models.UserLoginStatus
import com.notarmaso.beeritupcompose.models.UserRecieve
import com.notarmaso.db_access_setup.models.Kitchen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class StateHandler(ctx: Context) {

    var sharedPref: SharedPreferences
    val context = ctx


    init {
        println("initing statehandler")
        val activity: Activity = context as Activity
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    }

    private val userRepo = UserRepository
    private val kitchenRepo = KitchenRepository


    /* STATE CREATION */
    private var _appMode: AppMode = AppMode.SignedOut
    val appMode: AppMode get() = _appMode

    sealed class AppMode(open val kId: Int, open val uId: Int) {

        data class SignedInAsUser(
            override val uId: Int,
            val uName: String,
            val uPin: Int,
            val isAssigned: Boolean,
            override val kId: Int
        ) : AppMode(kId, uId)

        data class SignedInAsKitchen(override val kId: Int, val kPass: String, val kName: String, override val uId: Int, val uName: String) : AppMode(kId, uId)
        object SignedOut : AppMode(-1, -1)
    }


    fun setAppMode(appMode: AppMode){
        _appMode = appMode

    }




    /*When logged in successfully*/
    fun onUserSignInSuccess(user: UserRecieve, uStatus: UserLoginStatus) {
        _appMode = AppMode.SignedInAsUser(user.id, user.name, user.pin, uStatus.isAssigned, uStatus.kId)

        /*This needs to be run to save details with an app close*/
        saveUserDetailsInPrefs(user.id)
    }

    fun onKitchenSignInSuccess(kitchen: Kitchen) {
        _appMode = AppMode.SignedInAsKitchen(kitchen.id, kitchen.pass, kitchen.name, -1, "")

        /*This needs to be run to save details with an app close*/
        saveKitchenDetailsInPrefs(kitchen.id)
    }


    private fun saveKitchenDetailsInPrefs(kId: Int) {
        with(sharedPref.edit()) {
            putBoolean("LoggedInAsKitchen", true)
            putInt("kId", kId)
            apply()
        }
    }

    private fun saveUserDetailsInPrefs(userId: Int) {
        with(sharedPref.edit()) {
            putBoolean("LoggedInAsUser", true)
            putInt("uId", userId)

            apply()
        }
    }


    /*If user was logged in, recover details*/
    fun onUserWasLoggedIn() {

        val uId = sharedPref.getInt("uId", -1)

        CoroutineScope(Dispatchers.IO).launch {

            //Get Assigned Details plus Kitchen ID
            val response: Response<UserRecieve> = userRepo.getUser(uId)

            if (response.code() == 200) {
                val user = response.body()
                val uStatus = user?.let { getAssignedDetails(it.id) }
                if (uStatus != null) {
                    _appMode = AppMode.SignedInAsUser(user.id, user.name, user.pin, uStatus.isAssigned, uStatus.kId)
                }

            } else{
                logOut()}
        }
    }


    /*If kitchen was logged in, recover details*/
    fun onKitchenWasLoggedIn() {
        val kId = sharedPref.getInt("kId", -1)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<Kitchen> = kitchenRepo.getKitchen(kId)
            if (response.code() == 200) {
                val kitchen = response.body()

                if (kitchen != null) {
                    _appMode = AppMode.SignedInAsKitchen(kitchen.id, kitchen.pass, kitchen.name, -1, "")
                }
            } else logOut()
        }
    }


    /*Returns object consisting of if user is assigned and assigned kitchen id */
    suspend fun getAssignedDetails(userId: Int): UserLoginStatus? {

        println("GETTING ASSIGNEDD DETIAL")
        return userRepo.isAssigned(userId).body()
    }



    /*When user joins kitchen*/
    fun onUserJoinedKitchen(kId: Int) {
        val currentUser = _appMode as AppMode.SignedInAsUser
        _appMode = AppMode.SignedInAsUser(currentUser.uId, currentUser.uName, currentUser.uPin,true, kId)

    }





    fun wasLoggedInUser(): Boolean {
        return sharedPref.getBoolean("LoggedInAsUser", false)
    }

    fun wasLoggedInKitchen(): Boolean {
        return sharedPref.getBoolean("LoggedInAsKitchen", false)
    }

    fun logOut() {

        when (appMode) {
            is AppMode.SignedInAsUser -> {
                println("Signed in as user")
                with(sharedPref.edit()) {
                    putBoolean("LoggedInAsUser", false)

                    apply()
                }
            }
            is AppMode.SignedInAsKitchen -> {
                println("Signed in as kitchen")
                with(sharedPref.edit()) {
                    putBoolean("LoggedInAsKitchen", false)
                    apply()
                }
            }
            else -> {
                println("Not signed IN")
            }
        }
        onLogOut()

    }

    private fun onLogOut() {
        _appMode = AppMode.SignedOut
    }



}

fun StateHandler.AppMode.isSignedInAsUser() = when(this){

    is StateHandler.AppMode.SignedInAsKitchen -> false
    is StateHandler.AppMode.SignedInAsUser ->  true
    StateHandler.AppMode.SignedOut -> false
}

fun StateHandler.AppMode.isSignedInAsKitchen() = when(this){
    is StateHandler.AppMode.SignedInAsKitchen -> true
    is StateHandler.AppMode.SignedInAsUser -> false
    StateHandler.AppMode.SignedOut -> false
}
