package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name= "name") var name: String,
    @ColumnInfo(name= "phone") var phone: String,
    @ColumnInfo(name = "owedFrom") var owedFrom: String,
    @ColumnInfo(name = "owesTo") var owesTo: String,
    @ColumnInfo(name = "totalBeers") var totalBeers: String,
    @ColumnInfo(name =  "totalSpentDKK") var totalSpentDkk: Float,
    @ColumnInfo(name =  "totalAddedDKK") var totalAddedDkk: Float,
    @ColumnInfo(name = "logBeersBought") var beersBoughtLog: String,
    @ColumnInfo(name = "logTransactions") var transactionsLog: String,
    @ColumnInfo(name = "logBeersAdded") var beersAddedLog: String
    )

data class UserEntry(val name: String, val price: Float, val phone: String)

data class UserLeaderboard(val name: String, val count: Int)