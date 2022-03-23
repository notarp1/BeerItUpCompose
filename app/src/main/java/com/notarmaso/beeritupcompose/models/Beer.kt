package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true) val beerId: Int = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Price")val price: Float,
    @ColumnInfo(name = "Owner") val owner: String
)