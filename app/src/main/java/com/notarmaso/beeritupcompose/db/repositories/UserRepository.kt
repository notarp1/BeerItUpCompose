package com.notarmaso.beeritupcompose.db.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.db.UserDao
import com.notarmaso.beeritupcompose.models.User


class UserRepository(ctx: Context) {

    private var userDao: UserDao


    init {
        val database =  AppDatabase.getDatabase(ctx)
        userDao = database.userDao()
    }

    suspend fun getAllUsers(): MutableList<User> {
        return userDao.getAll()
    }

    suspend fun getUser(name: String): User {
        return  userDao.getUser(name)
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAll(){
        userDao.deleteAll()
    }
}