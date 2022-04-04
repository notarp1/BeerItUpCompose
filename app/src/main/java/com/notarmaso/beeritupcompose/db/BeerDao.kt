package com.notarmaso.beeritupcompose.db

import androidx.room.*
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.User

@Dao
interface BeerDao {


    @Query ("SELECT * FROM BeerGroup WHERE BeerGroupName LIKE :beer")
    fun getAllFromGroup(beer: String): BeerGroup

    @Insert
    fun insertBeerList(beerGroup: BeerGroup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateBeerGroup(beerGroup: BeerGroup)

    @Query("SELECT * FROM BeerGroup")
    fun getAllGroups(): MutableList<BeerGroup>

    @Query("DELETE FROM BeerGroup")
    fun deleteAll()
}