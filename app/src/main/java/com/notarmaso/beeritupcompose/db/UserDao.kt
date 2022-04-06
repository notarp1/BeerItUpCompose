package com.notarmaso.beeritupcompose.db

import androidx.room.*
import com.notarmaso.beeritupcompose.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): MutableList<User>

    @Query("SELECT * FROM User WHERE name =:username")
    fun getUser(username: String): User

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query("SELECT logBeersAdded FROM User WHERE name =:username")
    fun getBeersAdded(username: String): String
    @Query("SELECT logBeersBought FROM User WHERE name =:username")
    fun getBeersBought(username: String): String
    @Query("SELECT logTransactions FROM User WHERE name =:username")
    fun getTransactions(username: String): String

    @Query("SELECT totalAddedDKK FROM User WHERE name =:username")
    fun getTotalAdded(username: String): Float

    @Query("SELECT totalSpentDKK FROM User WHERE name =:username")
    fun getTotalBought(username: String): Float

}