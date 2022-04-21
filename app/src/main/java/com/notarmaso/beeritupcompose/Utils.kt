package com.notarmaso.beeritupcompose

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.User
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

val gson = Gson()


enum class Pages(val value: String) {
    MAIN_MENU("main_menu"),
    SELECT_USER("select_user"),
    BUY_BEER("buy_beer"),
    SELECT_BEER_QUANTITY("select_beer_quantity"),
    ADD_USER("add_user"),
    DEBUG_DRAWER("debug_drawer"),
    ADD_BEER("add_beer"),
    PAYMENTS("payments"),
    LOG_BOOKS("log_books"),
    DELETE_USER("delete_user"),
    EDIT_USER("edit_user"),
    PIN_PAD("pin_pad")
}





fun deserializeBeerGroup(beers: String): MutableList<Beer>?{

    val itemType = object : TypeToken<MutableList<Beer>?>() {}.type

    return gson.fromJson(beers, itemType)

}
fun serializeBeerGroup(it: MutableList<Beer>?): String{
    return  gson.toJson(it)
}




/* Extension Functions */
fun String.fromJsonToListFloat() : MutableMap<String, Float> {

    val itemType = object : TypeToken<MutableMap<String, Float?>>() {}.type

    return gson.fromJson(this, itemType)
}


fun Float.roundOff(): String {
    val df = DecimalFormat("##.##")
    return df.format(this)
}

fun MutableMap<String, Float>.fromListFloatToJson() : String {
    return gson.toJson(this)
}

fun MutableMap<String, Int>.fromListIntToJson() : String {
    return gson.toJson(this)
}


fun String.fromJsonToListReversed() :  MutableList<String>{
        val itemType = object : TypeToken<MutableList<String>>() {}.type
        val list: MutableList<String> = gson.fromJson(this, itemType)
        list.reverse()
        return list
}

fun String.fromJsonToList() :  MutableList<String>{

    val itemType = object : TypeToken<MutableList<String>>() {}.type
    return gson.fromJson(this, itemType)
}
fun MutableList<String>.fromListToJson() : String {
    return gson.toJson(this)
}



fun String.fromJsonToListInt() : MutableMap<String, Int> {

    val itemType = object : TypeToken<MutableMap<String, Int>>() {}.type

    return gson.fromJson(this, itemType)
}


