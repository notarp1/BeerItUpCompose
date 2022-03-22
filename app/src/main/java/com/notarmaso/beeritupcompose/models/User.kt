package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name= "name") val name: String,
    @ColumnInfo(name= "phone") val phone: String,
    @ColumnInfo(name = "owedFrom") var owedFrom: Map<String, Double>?,
    @ColumnInfo(name = "owesTo") var owesTo: Map<String, Double>?
    )