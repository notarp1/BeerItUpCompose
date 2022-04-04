package com.notarmaso.beeritupcompose.models

import androidx.compose.runtime.mutableStateMapOf
import com.notarmaso.beeritupcompose.R
import com.notarmaso.beeritupcompose.fromListToJson

object SampleData {
    // Sample conversation data
    private val owedFrom: MutableMap<String, Float> = mutableStateMapOf()
    private val owesTo: MutableMap<String, Float> = mutableStateMapOf()
    val userListSample = listOf(
        User("Christian", "22222222", owedFrom.fromListToJson(), owesTo.fromListToJson(), 0),
        User("Mads", "22222222", owedFrom.fromListToJson(), owesTo.fromListToJson(), 0),
        User("Mathilde","22222222", owedFrom.fromListToJson(), owesTo.fromListToJson(), 0),
        User("Lase", "22222222",owedFrom.fromListToJson(), owesTo.fromListToJson(), 0)
    )


    val beerListSample = listOf(
        GlobalBeer(
            R.drawable.carlsberg,
            "Carlsberg"
        ),
        GlobalBeer(
            R.drawable.classic,
            "Classic"
        ),
        GlobalBeer(
            R.drawable.rexport,
            "Royal Export"
        ),
        GlobalBeer(
            R.drawable.tuborg,
            "Tuborg"
        ),
        GlobalBeer(
            R.drawable.raa,
            "Tuborg Rå"
        ),
        GlobalBeer(
            R.drawable.guld,
            "Gylden Giz"
        ),
        GlobalBeer(
            R.drawable.heineken,
            "Heineken"
        ),
        GlobalBeer(
            R.drawable.carlsspecial,
            "Carls Special"
        ),
        GlobalBeer(
            R.drawable.rclassic,
            "Royal Classic"
        ),
        GlobalBeer(
            R.drawable.rpilsner,
            "Royal Pilsner"
        ),
        GlobalBeer(
            R.drawable.julebryg,
            "Julebryg"
        ),


    )
}