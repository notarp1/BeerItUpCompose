package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/* DELETE */
data class GlobalBeer(
    val image: Int,
    val name: String,
    val price: Int = 5,
    var count: Int = 22)

