package com.notarmaso.beeritupcompose.db

import androidx.room.*
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.User

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer")
    fun getAll(): MutableList<Beer>

    @Insert
    fun insert(beer: Beer)

    @Update
    fun updateBeer(beerList: MutableList<Beer>)

    @Delete
    fun deleteBeer(beer: Beer)
}