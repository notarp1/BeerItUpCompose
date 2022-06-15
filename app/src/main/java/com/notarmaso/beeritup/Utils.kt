package com.notarmaso.beeritup

import com.google.gson.Gson

val gson = Gson()


enum class Pages(val value: String) {
    START_MENU("start_menu"),
    MAIN_MENU("main_menu"),
    ADD_USER("add_user"),
    ADD_USER_STEP2("add_user_step_2"),
    ADD_KITCHEN("add_kitchen"),
    ADD_KITCHEN_STEP2("add_kitchen_step_2"),
    ADD_BEVERAGE_STAGE_1("add_beverage_1"),
    ADD_BEVERAGE_STAGE_2("add_beverage_2"),
    SELECT_BEVERAGE_STAGE_1("select_beverage_stage_1"),
    SELECT_BEVERAGE_STAGE_2("select_beverage_stage_2"),
    LOGIN_USER("login_user"),
    LOGIN_KITCHEN("login_kitchen"),
    JOIN_KITCHEN("join_kitchen"),
    USER_SELECTION("user_selection"),
    STATISTICS("statistics"),
    PAYMENTS("payments"),
    LOGBOOKS("logbooks"),


    DEBUG_DRAWER("debugDaber"),
    DELETE_USER("delete_user"),
    EDIT_USER("edit_user"),
    PIN_PAD("pin_pad")
}


enum class Category(val category: String){
    BEERS("beer"),
    SOFT_DRINKS("soda"),
    MONEY_YOU_ARE_OWED("money_you_are_owed"),
    MONEY_YOU_OWE("money_you_owe")
}


enum class FuncToRun(){
    GET_LOGIN_STATE,
    GET_USERS,
    FINISHED_LOADING,
    GET_BEVERAGE_TYPES,
    GET_BEVERAGES_IN_STOCK,
    GET_PAYMENTS,
    GET_LOGIN_STATE_2,
    GET_SPECIFIC_BEV_STOCK,
    GET_LOGS
}



