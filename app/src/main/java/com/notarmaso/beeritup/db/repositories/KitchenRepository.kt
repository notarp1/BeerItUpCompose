package com.notarmaso.beeritup.db.repositories

import com.notarmaso.beeritup.dal.DBInstance
import com.notarmaso.beeritup.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class KitchenRepository(val dbInstance: DBInstance) : IKitchenRepository {

    override suspend fun getKitchen(id: Int): Response<Kitchen> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.getKitchen(id)
        }

        response.await()
    }

    override suspend fun isNameAvailable(name: String): Response<Boolean> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.isNameAvailable(name)
            }

            response.await()
        }

    override suspend fun addKitchen(kitchenToPost: KitchenToPost): Response<String> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.addKitchen(kitchenToPost = kitchenToPost)
            }

            response.await()
        }


    override suspend fun login(kitchenLoginObject: KitchenLoginObject): Response<Kitchen> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.login(kitchenLoginObject)
            }
            response.await()
        }

    override suspend fun addKitchenUser(kId: Int, uId: Int): Response<String> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.addKitchenUser(kId, uId)
            }

            response.await()
        }

    override suspend fun getKitchenUsers(kId: Int): Response<List<UserRecieve>> =
        coroutineScope {

            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.getKitchenUsers(kId)
            }

            response.await()

        }

    override suspend fun getBeverageTypes(
        kId: Int,
        type: String,
    ): Response<MutableList<BeverageType>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.getBeverageTypes(kId, type)
            }
            response.await()
        }

    override suspend fun getBeverageInStock(
        kId: Int,
        bevTypeId: Int
    ): Response<List<Beverage>> = coroutineScope{

            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.getBeverageInStock(kId, bevTypeId)
            }

            response.await()
    }

    override suspend fun getBeveragesInStock(
        kId: Int,
        type: String,
    ): Response<List<BeverageType>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.kitchenApi.getBeveragesInStock(kId, type)
            }
            response.await()
        }

    override suspend fun addBeverages(
        beverage: BeverageDBEntryObject,
        kId: Int,
        name: String,
    ): Response<String> = coroutineScope {

        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.addBeverages(beverage, kId, name)
        }

        response.await()
    }

    override suspend fun calculatePrice(
        kId: Int,
        beveragePurchaseConfigObj: BeveragePurchaseConfigObj,
    ): Response<BeveragePurchaseReceiveObj> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.calculatePrice(kId, beveragePurchaseConfigObj)
        }

        response.await()
    }

    override suspend fun acceptTransaction(
        kId: Int,
        uId: Int,
        beverage: List<Beverage>,
    ): Response<String> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.acceptTransaction(kId, uId, beverage)
        }

        response.await()
    }

    override suspend fun getMonthlyLeaderboard(
        kId: Int,
        year: Int,
        month: Int,
    ): Response<List<LeaderboardEntryObj>> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.getMonthlyLeaderboard(kId, year, month)
        }

        response.await()
    }

    override suspend fun getYearlyLeaderboard(
        kId: Int,
        year: Int,
    ): Response<List<LeaderboardEntryObj>> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.kitchenApi.getYearlyLeaderboard(kId, year)
        }
        response.await()
    }
}