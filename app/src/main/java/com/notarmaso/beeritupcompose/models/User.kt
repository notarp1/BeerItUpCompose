package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date
data class User(
    @SerializedName("_id")
    var name: String,
    var phone: String,
    var owedFrom: String,
    var owesTo: String,
    var totalBeers: String,
    var totalSpentDkk: String,
    var totalAddedDkk: String,
    var beersBoughtLog: String,
    var transactionsLog: String,
    var beersAddedLog: String
)

data class UserToPost(
    @SerializedName("uName")
    var name: String,
    @SerializedName("uPhone")
    var phone: String,
    @SerializedName("uPass")
    var password: String,
    @SerializedName("uPin")
    var pin: Int

)


data class UserRecieve(
    var id: Int,
    @SerializedName("uName")
    var name: String,
    @SerializedName("uPhone")
    var phone: String,
    @SerializedName("uPin")
    var pin: Int
)


data class UserLoginStatus(val isAssigned: Boolean, val kId: Int)
data class UserLoginObject(val uPhone: String, val uPass: String)
data class UserPaymentObject(val name: String, val phone: String, val uId: Int, val total: Int)

data class LeaderboardEntryObj(val name: String, val count: Int)


data class BeverageLogEntryObj(
    val count: Int,
    val price: Int,
    @SerializedName("name", alternate = ["buyer", "owner"])
    val counterpartName: String,
    @SerializedName(value = "added", alternate = ["removed", "settled"])
    val date: java.util.Date,
    @SerializedName(value = "you")
    val userName: String?,
    @SerializedName(value = "ownerPhone")
    val counterpartPhone: String?,
    val bevName: String
)
