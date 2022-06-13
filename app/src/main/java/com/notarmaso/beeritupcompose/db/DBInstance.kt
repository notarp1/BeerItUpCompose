package com.notarmaso.db_access_setup.dal

import com.notarmaso.beeritupcompose.db.repositories.IKitchenRepository
import com.notarmaso.beeritupcompose.db.repositories.IUserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DBInstance {
    /*TODO DONT CRASH IF SERVER IS OFFLINE*/
    private val retrofit by lazy {
       Retrofit.Builder()
            .baseUrl("http://10.0.1.221:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        /*Retrofit.Builder()
            .baseUrl("http://192.168.0.110:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/

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