package com.example.yesno.repository

import com.example.yesno.data.YesNo
import com.example.yesno.api.YesNoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import javax.inject.Inject

class DefaultYesNoDataSource @Inject constructor(
    okHttpClient: OkHttpClient
) : YesNoDataSource {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val service = Retrofit.Builder()
        .baseUrl("https://yesno.wtf/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        .create(YesNoService::class.java)

    override suspend fun fetch(force: String?): YesNo {
        val response = service.fetch(force)

        if (response.isSuccessful) {
            return response.body() ?: throw IOException("no body")
        }

        throw Exception(response.message())
    }
}

// curl https://yesno.wtf/api

// {"answer":"yes","forced":false,"image":"https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"}%
// {"answer":"no","forced":false,"image":"https://yesno.wtf/assets/no/12-dafd576be23d3768641340f76658ddfe.gif"}%

// option : force = yes / no / maybe
// curl https://yesno.wtf/api?force=maybe
//{"answer":"maybe","forced":true,"image":"https://yesno.wtf/assets/maybe/1-77b7fe92ff17b15ab4537c2bfafe16d1.gif"}%
