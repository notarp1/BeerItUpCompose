package com.notarmaso.beeritupcompose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.User

@Database(entities = [User::class, BeerGroup::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun beerDao(): BeerDao
}