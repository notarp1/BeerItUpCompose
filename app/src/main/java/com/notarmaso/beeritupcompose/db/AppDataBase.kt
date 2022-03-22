package com.notarmaso.beeritupcompose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.User

@Database(entities = [User::class, Beer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun beerDao(): BeerDao
}