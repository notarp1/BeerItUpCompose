package com.notarmaso.beeritup.models

import com.google.gson.annotations.SerializedName

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
    var pin: Int,
    @SerializedName("uEmail")
    var email: String

)


data class UserRecieve(
    var id: Int,
    @SerializedName("uName")
    var name: String,
    @SerializedName("uPhone")
    var phone: String,
    @SerializedName("uPin")
    var pin: Int,
    @SerializedName("uEmail")
    var email: String
)


data class UserLoginStatus(val isAssigned: Boolean, val kId: Int)
data class UserLoginObject(val uEmail: String, val uPass: String)
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
