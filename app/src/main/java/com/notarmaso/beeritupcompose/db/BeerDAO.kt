package com.notarmaso.beeritupcompose.db

import androidx.room.*
import com.notarmaso.beeritupcompose.models.User

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer")
    fun getAll(): List<User>

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser()

    @Delete
    fun deleteUser(user: User)
}