package com.notarmaso.beeritupcompose

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.User
val gson = Gson()



/*
fun serializeUser(it: User?): String{
    return  gson.toJson(it)
}
*/

/*fun deserializeUser(it: String): User {
    return gson.fromJson(it, User::class.java)
}*/

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

fun MutableMap<String, Float>.fromListFloatToJson() : String {
    return gson.toJson(this)
}

fun MutableMap<String, Int>.fromListIntToJson() : String {
    return gson.toJson(this)
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


