package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Beer(
   val name: String,
   val price: Float,
   val owner: String
)


@Entity
data class BeerGroup(
    @PrimaryKey @ColumnInfo(name = "BeerGroupName") val groupName: String,
    @ColumnInfo(name = "BeerList")val beers: String
)