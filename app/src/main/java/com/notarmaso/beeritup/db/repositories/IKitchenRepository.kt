package com.notarmaso.beeritup.db.repositories

import com.notarmaso.beeritup.models.*
import com.notarmaso.db_access_setup.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IKitchenRepository {


    @POST("kitchens")
    suspend fun addKitchen(@Body kitchenToPost: KitchenToPost): Response<String>

    @POST("kitchens/login")
    suspend fun login(@Body kitchenLoginObject: KitchenLoginObject): Response<Kitchen>

    @GET("kitchens/{id}")
    suspend fun getKitchen(@Path("id") id:Int): Response<Kitchen>

    @GET("kitchens/name_check/{name}")
    suspend fun isNameAvailable(@Path("name") name:String): Response<Boolean>

    @POST("kitchens/{id}/users/add/{uId}")
    suspend fun addKitchenUser(@Path("id") kId: Int, @Path("uId") uId: Int): Response<String>

    @GET("kitchens/{id}/users")
    suspend fun getKitchenUsers(@Path("id") kId: Int): Response<List<UserRecieve>>

    @GET("kitchens/{id}/beverages/all/{type}")
    suspend fun getBeverageTypes(@Path("id") kId: Int, @Path("type") type: String): Response<MutableList<BeverageType>>

    @GET("kitchens/{id}/beverages/stock/specific/{beverageId}")
    suspend fun getBeverageInStock(@Path("id") kId: Int, @Path("beverageId") bevTypeId: Int): Result<MutableList<Beverage>>

    @GET("kitchens/{id}/beverages/stock/all/{type}")
    suspend fun getBeveragesInStock(@Path("id") kId: Int, @Path("type") type: String): Response<MutableList<BeverageType>>

    @POST("kitchens/{id}/beverages/add/{beverage}")
    suspend fun addBeverages(@Body beverage: BeverageDBEntryObject, @Path("id") kId: Int, @Path("beverage") name:String ): Response<String>

    @POST("kitchens/{id}/beverages/price-calculation")
    suspend fun calculatePrice(@Path("id") kId: Int, @Body beveragePurchaseConfigObj: BeveragePurchaseConfigObj): Response<BeveragePurchaseReceiveObj>

    @POST("kitchens/{id}/beverages/transaction-accepted/{uId}")
    suspend fun acceptTransaction(@Path("id") kId: Int, @Path("uId") uId: Int, @Body beverage: List<Beverage>): Response<String>


    @GET("kitchens/{id}/leaderboard/{year}/{month}")
    suspend fun getMonthlyLeaderboard(@Path("id") kId: Int, @Path("year") year: Int, @Path("month") month: Int) : Response<List<LeaderboardEntryObj>>
    @GET("kitchens/{id}/leaderboard/{year}")
    suspend fun getYearlyLeaderboard(@Path("id") kId: Int, @Path("year") year: Int) : Response<List<LeaderboardEntryObj>>
}