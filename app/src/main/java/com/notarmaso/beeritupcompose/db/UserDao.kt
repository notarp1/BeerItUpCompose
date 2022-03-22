package com.notarmaso.beeritupcompose.db

import androidx.room.*
import com.notarmaso.beeritupcompose.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser()

    @Delete
    fun deleteUser(user: User)
}