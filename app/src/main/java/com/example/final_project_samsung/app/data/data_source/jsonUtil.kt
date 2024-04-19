package com.example.final_project_samsung.app.data.data_source

import com.google.gson.Gson

fun listOfIntToJson(value: List<Int>): String = Gson().toJson(value)
fun listOfStringToJson(value: List<String>): String = Gson().toJson(value)
fun jsonToList(value: String): List<String> =
    Gson().fromJson(value, Array<String>::class.java).toList()