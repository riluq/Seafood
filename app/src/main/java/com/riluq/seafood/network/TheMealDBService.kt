package com.riluq.seafood.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface TheMealDBService {
    @GET("filter.php")
    fun getSeafoodAsync(@Query("c") filter: String ):
            Deferred<SeafoodResponse>

    @GET("lookup.php")
    fun getMealDetailAsync(@Query("i") id: String ):
            Deferred<MealsDetailResponse>
}

object TheMealDBApi {
    val retrofitService: TheMealDBService by lazy {
        retrofit.create(TheMealDBService::class.java)
    }
}