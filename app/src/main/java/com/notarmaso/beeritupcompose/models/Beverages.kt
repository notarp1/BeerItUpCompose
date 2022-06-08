package com.notarmaso.beeritupcompose.models

import com.google.gson.annotations.SerializedName
import java.util.Date


data class Beverage(
   val id: Int,
   @SerializedName("beverageTypeId")
   val bevId: Int,
   val price: Int,
   @SerializedName("kitchenId")
   val kId: Int,
   @SerializedName("beverageOwnerId")
   var bevOwnerId: Int,
   val createdAt: Date,
   @SerializedName("beverageDrinkerId")
   var bevDrinkerId: Int?,
   val removedAt: Date?,
   val settleDate: Date?

)



data class BeverageType(
    val beverageTypeId: Int,
    val kId: Int = 2,
    @SerializedName("beverageName")
    val name: String,
    @SerializedName("beverageType")
    val type: String,
    val pictureUrl: String,
    val stock: Int
)

data class BeverageDBEntryObject(val quantity: Int, val price: Int, val uId: Int)
data class BeveragePurchaseConfigObj(val bName: String, val count: Int)
data class BeveragePurchaseReceiveObj(val price: Float, val beverages: List<Beverage>)