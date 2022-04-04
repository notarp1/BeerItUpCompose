package com.notarmaso.beeritupcompose.db.repositories
import android.content.Context
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.db.BeerDao
import com.notarmaso.beeritupcompose.models.BeerGroup

class BeerRepository(ctx: Context) {
   private var beerDao: BeerDao

    init {
        val database =  AppDatabase.getDatabase(ctx)
        beerDao = database.beerDao()
    }

    suspend fun getAllBeerGroups(): MutableList<BeerGroup> {
        return beerDao.getAllGroups()
    }

    suspend fun getFromGroup(beerGroup: String): BeerGroup {
        return beerDao.getAllFromGroup(beerGroup)
    }

    suspend fun insertBeerList(beerGroup: BeerGroup) {
        beerDao.insertBeerList(beerGroup)
    }
    suspend fun updateBeerGroup(beerGroup: BeerGroup) {
        beerDao.updateBeerGroup(beerGroup)
    }

    suspend fun deleteAll() {
        beerDao.deleteAll()
    }
}