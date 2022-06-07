package com.notarmaso.db_access_setup.dal

import com.google.gson.GsonBuilder
import com.notarmaso.beeritupcompose.db.repositories.IKitchenRepository
import com.notarmaso.beeritupcompose.db.repositories.IUserRepository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.ConnectException


object DBInstance {

    private val retrofit by lazy {
       Retrofit.Builder()
            .baseUrl("http://10.0.1.221:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        /*Retrofit.Builder()
            .baseUrl("http://172.20.10.7:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/


    }

    val userApi: IUserRepository by lazy {
        retrofit.create(IUserRepository::class.java)
    }

    val kitchenApi: IKitchenRepository by lazy {
        retrofit.create(IKitchenRepository::class.java)
    }

}