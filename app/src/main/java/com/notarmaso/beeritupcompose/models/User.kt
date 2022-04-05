package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name= "name") val name: String,
    @ColumnInfo(name= "phone") val phone: String,
    @ColumnInfo(name = "owedFrom") var owedFrom: String,
    @ColumnInfo(name = "owesTo") var owesTo: String,
    @ColumnInfo(name = "totalBeers") var totalBeers: String
    )

data class UserEntry(val name: String, val price: Float, val phone: String)

data class UserLeaderboard(val name: String, val count: Int)