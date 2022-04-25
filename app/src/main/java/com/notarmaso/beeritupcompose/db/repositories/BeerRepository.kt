package com.notarmaso.beeritupcompose.db.repositories
import android.content.Context
import com.notarmaso.beeritupcompose.db.AppDatabase
import com.notarmaso.beeritupcompose.db.BeerDao
import com.notarmaso.beeritupcompose.models.BeerGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeerRepository(ctx: Context) {
   private var beerDao: BeerDao

    init {
        val database =  AppDatabase.getDatabase(ctx)
        beerDao = database.beerDao()
    }

    suspend fun getAllBeerGroups(): MutableList<BeerGroup> {
        val beerGroups: MutableList<BeerGroup>
        withContext(Dispatchers.IO){
            beerGroups =  beerDao.getAllGroups()

        }
        return beerGroups

    }

    suspend fun getFromGroup(beerGroup: String): BeerGroup {

        val beerGroups: BeerGroup
        withContext(Dispatchers.IO){
            beerGroups = beerDao.getAllFromGroup(beerGroup)
        }
        return beerGroups
    }

    suspend fun insertBeerList(beerGroup: BeerGroup) {
        withContext(Dispatchers.IO) {
            beerDao.insertBeerList(beerGroup)
        }
    }
    suspend fun updateBeerGroup(beerGroup: BeerGroup) {
        withContext(Dispatchers.IO){
            beerDao.updateBeerGroup(beerGroup)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO){
            beerDao.deleteAll()

        }
    }
}