package com.example.yesno.api

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// {
//  "answer": "yes",
//  "forced": false,
//  "image": "https://yesno.wtf/assets/yes/2.gif"
//}

data class YesNo(
    @Json(name = "answer")
    val answer: String,

    @Json(name = "forced")
    val forced: Boolean,

    @Json(name = "image")
    val image: String,
)

interface YesNoService {
    @GET("api")
    suspend fun fetch(
        @Query("force") force: String? = null
    ): Response<YesNo>
}