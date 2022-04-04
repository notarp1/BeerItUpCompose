package com.notarmaso.beeritupcompose

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerService(val service: Service, val beerObs: BeerObserverNotifier) {

    var beerList: MutableList<Beer>? = null
    val gson = Gson()
    enum class Brands(val bName: String) {
        CARLSBERG(bName = "Carlsberg"),
        TUBORG(bName = "Tuborg"),
        CLASSIC(bName = "Classic"),
        CARLS_SPECIAL(bName = "Carls Special"),
        GULD_DAME(bName = "Gylden Giz"),
        HEINEKEN(bName = "Heineken"),
        JULEBRYG(bName = "Julebryg"),
        RAA(bName = "Tuborg Rå"),
        R_CLASSIC(bName = "Royal Classic"),
        R_EXPORT(bName = "Royal Export"),
        R_PILSNER(bName = "Royal Pilsner")

    }


    var carlsberg: MutableList<Beer>? = mutableListOf()
    var turborg: MutableList<Beer>? = mutableListOf()
    var classic: MutableList<Beer>? = mutableListOf()
    var carlsSpecial: MutableList<Beer>? = mutableListOf()
    var guld: MutableList<Beer>? = mutableListOf()
    var heineken: MutableList<Beer>? = mutableListOf()
    var julebryg: MutableList<Beer>? = mutableListOf()
    var raa: MutableList<Beer>? = mutableListOf()
    var rClassic: MutableList<Beer>? = mutableListOf()
    var rExport: MutableList<Beer>? = mutableListOf()
    var rPilsner: MutableList<Beer>? = mutableListOf()

     var mapOfBeer: MutableMap<String, MutableList<Beer>?> = mutableMapOf(
        Brands.CARLSBERG.bName to carlsberg,
        Brands.TUBORG.bName to turborg,
        Brands.CLASSIC.bName to classic,
        Brands.CARLS_SPECIAL.bName to carlsSpecial,
        Brands.GULD_DAME.bName to guld,
        Brands.HEINEKEN.bName to heineken,
        Brands.JULEBRYG.bName to julebryg,
        Brands.RAA.bName to raa,
        Brands.R_CLASSIC.bName to rClassic,
        Brands.R_EXPORT.bName to rExport,
        Brands.R_PILSNER.bName to rPilsner
    )





    fun getStock(name: String): Int? {

        return when (name) {
            Brands.CARLSBERG.bName -> carlsberg?.count()
            Brands.TUBORG.bName -> turborg?.count()
            Brands.CLASSIC.bName -> classic?.count()
            Brands.CARLS_SPECIAL.bName -> carlsSpecial?.count()
            Brands.GULD_DAME.bName -> guld?.count()
            Brands.HEINEKEN.bName -> heineken?.count()
            Brands.JULEBRYG.bName -> julebryg?.count()
            Brands.RAA.bName -> raa?.count()
            Brands.R_CLASSIC.bName -> rClassic?.count()
            Brands.R_EXPORT.bName -> rExport?.count()
            Brands.R_PILSNER.bName -> rPilsner?.count()
            else -> 0
        }
    }


}
