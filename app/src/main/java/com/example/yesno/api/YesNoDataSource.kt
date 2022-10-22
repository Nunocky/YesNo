package com.example.yesno.api

import com.example.yesno.data.YesNo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

interface YesNoDataSource {
    suspend fun fetch(): Result<YesNo>
}

// curl https://yesno.wtf/api
//{"answer":"no","forced":false,"image":"https://yesno.wtf/assets/no/12-dafd576be23d3768641340f76658ddfe.gif"}%

// option : force = yes / no / maybe
// curl https://yesno.wtf/api?force=maybe
//{"answer":"maybe","forced":true,"image":"https://yesno.wtf/assets/maybe/1-77b7fe92ff17b15ab4537c2bfafe16d1.gif"}%


class YesNoDataSourceImpl @Inject constructor(

) : YesNoDataSource {

    private val client = OkHttpClient.Builder()
//        .authenticator(CachingAuthenticatorDecorator(digestAuthenticator, authCache))
//        .addInterceptor(AuthenticationCacheInterceptor(authCache))
//        .addInterceptor(logging)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val service = Retrofit.Builder()
        .baseUrl("https://yesno.wtf/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(YesNoService::class.java)

    override suspend fun fetch(): Result<YesNo> {
        val response = service.fetch()

        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        }

        return Result.failure(IOException("unknown"))
    }
}

interface YesNoService {
    @GET("api")
    suspend fun fetch(
        @Query("query") query: String? = null
    ): Response<YesNo>
}
