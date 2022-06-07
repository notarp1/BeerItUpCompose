package com.notarmaso.db_access_setup.models

import com.google.gson.annotations.SerializedName




data class KitchenToPost(
    @SerializedName("kName")
    var name: String,
    @SerializedName("kPass")
    var pass: String,
    @SerializedName("kPin")
    var pin: Int
    )



data class Kitchen(
    var id: Int,
    @SerializedName("kName")
    var name: String,
    @SerializedName("kPass")
    var pass: String,
    @SerializedName("kPin")
    var pin: Int

)

data class KitchenUser(
    @SerializedName("kId")
    var kId: Int,
    @SerializedName("uId")
    var uId: Int
)



data class KitchenLoginObject(val kName: String, val kPass: String)





