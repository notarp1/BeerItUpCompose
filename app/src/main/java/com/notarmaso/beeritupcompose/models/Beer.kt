package com.notarmaso.beeritupcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true) val beerId: Int,
    @ColumnInfo(name = "Beer") val name: String,
    @ColumnInfo(name = "Price")val price: Double,
    @ColumnInfo(name = "Owner") val owner: User
)