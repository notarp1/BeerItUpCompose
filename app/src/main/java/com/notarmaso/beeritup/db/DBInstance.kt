package com.notarmaso.beeritup.dal

import android.content.Context
import android.net.ConnectivityManager
import com.notarmaso.beeritup.db.repositories.IKitchenRepository
import com.notarmaso.beeritup.db.repositories.IUserRepository
import okhttp3.*
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class DBInstance(ctx: Context) {
    /*TODO DONT CRASH IF SERVER IS OFFLINE*/


    private val retrofit by lazy {
      /*  Retrofit.Builder()
            .baseUrl("https://beeritup-app.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(ctx).build())
            .build()*/


        Retrofit.Builder()
            .baseUrl("http://10.0.1.221:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(ctx).build())
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
    private fun getHttpClient(ctx: Context): OkHttpClient.Builder {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(NetworkConnectionInterceptor(ctx))



        return httpClient
    }

    class NetworkConnectionInterceptor(private val mContext: Context) : Interceptor {

        @Throws(IOException::class)

        override fun intercept(chain: Interceptor.Chain): Response {
           val request = chain.request()
            try {
               if (!isConnected) {
                   throw NoConnectivityException()
                   // Throwing our custom exception 'NoConnectivityException'
               }
               val builder: Request.Builder = request.newBuilder()
               return chain.proceed(request)
           }catch (e: Exception){

               e.printStackTrace()
               var msg = ""

               when (e) {
                   is NoConnectivityException -> {
                       msg = e.message
                   }
                   is SocketTimeoutException -> {
                       msg = "Timeout - Please check your internet connection"
                   }
                   is UnknownHostException -> {
                       msg = "Unable to make a connection. Please check your internet"
                   }
                   is ConnectionShutdownException -> {
                       msg = "Connection shutdown. Please check your internet"
                   }
                   is IOException -> {
                       msg = "Server is unreachable, please try again later."
                   }
                   is IllegalStateException -> {
                       msg = "${e.message}"
                   }
                   else -> {
                       msg = "${e.message}"
                   }
               }

               return Response.Builder()
                   .request(request)
                   .protocol(Protocol.HTTP_1_1)
                   .code(500)
                   .message(msg)
                   .body(ResponseBody.create(null, "{${e}}")).build()
           }

        }

        private val isConnected: Boolean
            get() {
                val connectivityManager =
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                return netInfo != null && netInfo.isConnected
            }
    }

    class NoConnectivityException : IOException() {
        // You can send any message whatever you want from here.
        override val message: String
            get() = "You are not connected to the internet"
        // You can send any message whatever you want from here.
    }


}


