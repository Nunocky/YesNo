package com.example.yesno.data

import com.squareup.moshi.Json

data class YesNo(

    @Json(name = "answer")
    val answer: String,

    @Json(name = "forced")
    val forced: Boolean,

    @Json(name = "image")
    val image: String,
)