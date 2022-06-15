package com.notarmaso.beeritup.db.repositories

import com.notarmaso.beeritup.models.*
import retrofit2.Response
import retrofit2.http.*


interface IUserRepository {
    @GET("users")
    suspend fun getUsers(): Response<MutableList<UserRecieve>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id:Int): Response<UserRecieve>

    @GET("users/email_check/{email}")
    suspend fun isEmailAvailable(@Path("email") email:String): Response<Boolean>

    @POST("users")
    suspend fun addUser(@Body userToPost: UserToPost): Response<UserRecieve>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id:String, @Body user: UserToPost): Response<String>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id:String): Response<String>

    @POST("users/login")
    suspend fun login(@Body userLoginObject: UserLoginObject): Response<UserRecieve>

    @GET("users/{id}/assigned")
    suspend fun isAssigned(@Path("id") userId: Int): Response<UserLoginStatus>

    @GET("users/{id}/owed")
    suspend fun userOwed(@Path("id") userId: Int): Response<List<UserPaymentObject>>

    @GET("users/{id}/owes")
    suspend fun userOwes(@Path("id") userId: Int): Response<List<UserPaymentObject>>

    @GET("users/{uId}/pay/{id}")
    suspend fun makePayment(@Path("uId") uId: Int, @Path("id") ownerId: Int): Response<String>

    @GET("users/{id}/logs/beverages/bought")
    suspend fun paidLogbook(@Path("id") uId: Int) : Response<List<BeverageLogEntryObj>>

    @GET("users/{id}/logs/beverages/sold")
    suspend fun soldLogbook(@Path("id") uId: Int) : Response<List<BeverageLogEntryObj>>

    @GET("users/{id}/logs/beverages/added")
    suspend fun addedLogbook(@Path("id") uId: Int) : Response<List<BeverageLogEntryObj>>

    @GET("users/{id}/logs/beverages/consumed")
    suspend fun consumedLogbook(@Path("id") uId: Int) : Response<List<BeverageLogEntryObj>>


}