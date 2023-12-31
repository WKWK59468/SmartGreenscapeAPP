package com.example.smartgreenscape.service

import com.example.smartgreenscape.model.Environmental
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EnvironmentalService {
    @GET("v2/aqx_p_142")
    fun getEnvironmental(
        @Query("api_key") key:String
    ):Call<Environmental>
}
private const val BASE_URL="https://data.moenv.gov.tw/api/"

object EnvironmentalApi{
    private val retrofit=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val retofitService:EnvironmentalService= retrofit.create(EnvironmentalService::class.java)
}