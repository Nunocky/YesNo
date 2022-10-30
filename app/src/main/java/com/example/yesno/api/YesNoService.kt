package com.example.yesno.api

import com.example.yesno.data.YesNo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// {
//  "answer": "yes",
//  "forced": false,
//  "image": "https://yesno.wtf/assets/yes/2.gif"
//}

interface YesNoService {
    @GET("api")
    suspend fun fetch(
        @Query("force") force: String? = null
    ): Response<YesNo>
}