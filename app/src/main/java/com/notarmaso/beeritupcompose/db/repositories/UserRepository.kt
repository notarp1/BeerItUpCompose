package com.notarmaso.beeritupcompose.db.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.db.UserDao
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepository(ctx: Context) {

    private var userDao: UserDao


    init {
        val database =  AppDatabase.getDatabase(ctx)
        userDao = database.userDao()
    }

    suspend fun getAllUsers(): MutableList<User> {
        val userList: MutableList<User>

        withContext(Dispatchers.IO){
           userList = userDao.getAll()
        }
        return userList
    }

    suspend fun getUser(name: String): User {
        val user: User
        withContext(Dispatchers.IO){
            user = userDao.getUser(name)
        }
        return user
    }

    suspend fun insertUser(user: User){
        withContext(Dispatchers.IO){
            userDao.insertUser(user)
        }
    }

    suspend fun updateUser(user: User){
        withContext(Dispatchers.IO){
            userDao.updateUser(user)
        }
    }

    suspend fun deleteUser(user: User){
        withContext(Dispatchers.IO){
            userDao.deleteUser(user)
        }
    }

    suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            userDao.deleteAll()
        }

    }

    suspend fun getTotalBought(user: String): Float {
        val totalBought: Float
        withContext(Dispatchers.IO){
           totalBought =  userDao.getTotalBought(user)
        }
        return totalBought
    }
    suspend fun getTotalAdded(user: String): Float {
        val totalAdded: Float
        withContext(Dispatchers.IO){
            totalAdded = userDao.getTotalAdded(user)
        }
        return totalAdded
    }

    suspend fun getAddedLog(user: String): String {
        val addedLog: String
        withContext(Dispatchers.IO){
            addedLog =  userDao.getBeersAdded(user)
        }
        return addedLog
    }

    suspend fun getBoughtLog(user: String): String{
       val boughtLog: String
        withContext(Dispatchers.IO){
            boughtLog = userDao.getBeersBought(user)
        }
        return boughtLog
    }

    suspend fun getTranscationsLog(user: String): String{
        val transactionLog: String
        withContext(Dispatchers.IO){
           transactionLog = userDao.getTransactions(user)
        }
        return transactionLog
    }


}